# 빌드 스테이지
FROM openjdk:21-slim as build
WORKDIR /workspace/app

# 애플리케이션의 소스 코드와 gradle 빌드 파일을 이미지로 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY src src

# gradlew에 실행 권한 부여
RUN chmod +x ./gradlew

# ARG DATABASE_PASSWORD
# ARG DATABASE_URL
# ARG DATABASE_USERNAME
# ARG JWT_SECRET
# ARG KAKAO_CLIENT_ID
# ARG KAKAO_CLIENT_SECRET
# ARG KAKAO_REDIRECT_URI
# ARG S3_ACCESS_KEY
# ARG S3_BUCKET
# ARG S3_SECRET_KEY

# ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}
# ENV DATABASE_URL=${DATABASE_URL}
# ENV DATABASE_USERNAME=${DATABASE_USERNAME}
# ENV JWT_SECRET=${JWT_SECRET}
# ENV KAKAO_CLIENT_ID=${KAKAO_CLIENT_ID}
# ENV KAKAO_CLIENT_SECRET=${KAKAO_CLIENT_ID}
# ENV KAKAO_REDIRECT_URI=${KAKAO_REDIRECT_URI}
# ENV S3_ACCESS_KEY=${S3_ACCESS_KEY}
# ENV S3_BUCKET=${S3_BUCKET}
# ENV S3_SECRET_KEY=${S3_SECRET_KEY} 

# 실행 가능한 JAR 파일 빌드
RUN ./gradlew build -x test

# 런타임 스테이지
FROM openjdk:21-slim
WORKDIR /app

# 빌드 스테이지에서 생성된 JAR 파일을 런타임 이미지로 복사
COPY --from=build /workspace/app/build/libs/*.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java","-jar","app.jar"]
# ENTRYPOINT ["java","-Dspring.profiles.active=deploy","-jar","app.jar"]
# ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar app.jar"]