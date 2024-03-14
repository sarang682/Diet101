package com.d101.back.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.d101.back.entity.*;
import com.d101.back.entity.composite.UserFoodKey;
import com.d101.back.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.d101.back.dto.IntakeDto;
import com.d101.back.dto.MealDto;
import com.d101.back.dto.QIntakeDto;
import com.d101.back.dto.request.CreateMealReq;
import com.d101.back.entity.Intake;
import com.d101.back.entity.Meal;
import com.d101.back.entity.User;
import com.d101.back.entity.composite.FoodMealKey;
import com.d101.back.entity.enums.Dunchfast;

import com.d101.back.exception.NoSuchDataException;
import com.d101.back.exception.UnAuthorizedException;
import com.d101.back.exception.response.ExceptionStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class DietService {
	
	private final DietRepository dietRepository;
	private final UserRepository userRepository;
	private final FoodRepository foodRepository;
	private final IntakeRepository intakeRepository;
	private final PreferenceRepository preferenceRepository;
	private final JPAQueryFactory jpaQueryFactory;
	private final S3Service s3Service;

	public List<MealDto> getMealsForSpecificDate(String email, String date) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new NoSuchDataException(ExceptionStatus.USER_NOT_FOUND));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		QMeal meal = QMeal.meal;
		QIntake intake = QIntake.intake;
		QFood food = QFood.food;
		// 그날 먹은 식사 가져오기
		List<Meal> meals = dietRepository.findByUserAndTime(user, localDate);
		return meals.stream().map(m -> {
			List<IntakeDto> intakeData = jpaQueryFactory
					.select(new QIntakeDto(food, intake.amount)).distinct()
					.from(meal, intake, food)
					.where(intake.key.meal_id.eq(m.getId()), intake.key.food_id.eq(food.id))
					.fetch();
			return new MealDto(m.getId(), m.getImage(), date, m.getType(), m.getTotalCalorie(), intakeData);
		}).toList();
	}
	
	public List<MealDto> getMealsForSpecificTerm(String email, String start, String end) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new NoSuchDataException(ExceptionStatus.USER_NOT_FOUND));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate = LocalDate.parse(start, formatter);
		LocalDate endDate = LocalDate.parse(end, formatter);
		List<Meal> meals = dietRepository.findByUserAndTimeBetween(user, startDate, endDate);

		QMeal meal = QMeal.meal;
		QIntake intake = QIntake.intake;
		QFood food = QFood.food;
		return meals.stream().map(m -> {
			List<IntakeDto> intakeData = jpaQueryFactory
					.select(new QIntakeDto(food, intake.amount)).distinct()
					.from(meal, intake, food)
					.where(intake.key.meal_id.eq(m.getId()), intake.key.food_id.eq(food.id))
					.fetch();
			return new MealDto(m.getId(), m.getImage(), m.getTime().toString(), m.getType(), m.getTotalCalorie(), intakeData);
		}).toList();
	}

	@Transactional
	public void saveMeal(String email, MultipartFile file, CreateMealReq req) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new NoSuchDataException(ExceptionStatus.USER_NOT_FOUND));

		// 이미지 저장
		String img = s3Service.saveFile(file, "diet");

		Meal meal = new Meal(user, img, Dunchfast.valueOf(req.getType()), req.getTime(),
				req.getTotalKcal(), req.getTotalCarbohydrate(),
				req.getTotalProtein(), req.getTotalFat());
		dietRepository.save(meal);

		List<Intake> intakes = req.getIntakes().stream()
				.map(intake -> new Intake(new FoodMealKey(intake.getFood_id(), meal.getId()), intake.getAmount()))
				.toList();
		intakeRepository.saveAll(intakes);

		// 선호도 증가
		intakes.forEach(intake -> {
			UserFoodKey key = new UserFoodKey(user.getId(), intake.getKey().getFood_id());
			Preference preference = preferenceRepository.findById(key)
					.orElseGet(() -> preferenceRepository.save(new Preference(key, 0)));
			preference.plusWeight();
		});
	}

	public Meal getMealById(Long id) {
        return dietRepository.findById(id)
				.orElseThrow(() -> new NoSuchDataException(ExceptionStatus.MEAL_NOT_FOUND));
	}
	
	public Boolean isUserHaveMeal(String email, Meal meal) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new NoSuchDataException(ExceptionStatus.USER_NOT_FOUND));
        return user.getMeals().contains(meal);
    }
	
	public MealDto getMealOfUserById(String email, Long id) {
		Meal meal = getMealById(id);

		if (isUserHaveMeal(email, meal)) {
			QMeal qMeal = QMeal.meal;
			QIntake intake = QIntake.intake;
			QFood food = QFood.food;
			List<IntakeDto> intakeData = jpaQueryFactory
					.select(new QIntakeDto(food, intake.amount)).distinct()
					.from(qMeal, intake, food)
					.where(intake.key.meal_id.eq(meal.getId()), intake.key.food_id.eq(food.id))
					.fetch();
			return new MealDto(meal.getId(), meal.getImage(), meal.getTime().toString(), meal.getType(), meal.getTotalCalorie(), intakeData);
		}
		throw new UnAuthorizedException(ExceptionStatus.UNAUTHORIZED);
	}

	public IntakeDto getFoodDto(Long meal_id, Long food_id, String email) {
		Meal meal = getMealById(meal_id);
		if (isUserHaveMeal(email, meal)) {
			Food food = this.foodRepository.findById(food_id)
					.orElseThrow(() -> new NoSuchDataException(ExceptionStatus.FOOD_NOT_FOUND));
			Intake intake = this.intakeRepository.findById(new FoodMealKey(food_id, meal_id))
					.orElseThrow(() -> new NoSuchDataException(ExceptionStatus.INTAKE_NOT_FOUND));
			return new IntakeDto(food, intake.getAmount());
		}
		throw new UnAuthorizedException(ExceptionStatus.UNAUTHORIZED);
	}

//	public IntakeDto getFoodDto(MealDto meal, Long food_id) {
//		List<IntakeDto> intake = meal.getIntake().stream()
//				.filter(i -> i.getFood().getId().equals(food_id)).toList();
//		if (!intake.isEmpty()) {
//			return intake.getFirst();
//		}
//		throw new NoSuchDataException(ExceptionStatus.FOOD_NOT_FOUND);
//	}
}
