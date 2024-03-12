package com.d101.back.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.d101.back.entity.Meal;
import com.d101.back.service.DietService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/diet")
@RequiredArgsConstructor
public class DietController {
	private final DietService dietService;
	
	@GetMapping("/info")
	public ResponseEntity<?> getTodayDietInfo(Authentication authentication) {
		List<Meal> meals = this.dietService.getMealsForSpecificDate(authentication.getName(), LocalDate.now().toString());
		return new ResponseEntity<>(meals, HttpStatus.OK);
	}
	
	@GetMapping("/info")
	public ResponseEntity<?> getDietInfoForDate(@RequestParam(value = "date") String date, Authentication authentication) {
		List<Meal> meals = this.dietService.getMealsForSpecificDate(authentication.getName(), date);
		return new ResponseEntity<>(meals, HttpStatus.OK);
	}
	
	@GetMapping("/info")
	public ResponseEntity<?> getDietInfoForTerm(@RequestParam(value = "dateFrom") String dateFrom, @RequestParam(value = "dateTo") String dateTo, Authentication authentication) {
		List<Meal> meals = this.dietService.getMealsForSpecificTerm(authentication.getName(), dateFrom, dateTo);
		return new ResponseEntity<>(meals, HttpStatus.OK);
	}
}
