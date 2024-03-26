package com.d101.back.service;


import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class ModelService {

    public String transmitImageToYolo(MultipartFile multipartFile) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        Resource resource = new ByteArrayResource(multipartFile.getBytes()) {
            @Override
            public String getFilename() {
                return multipartFile.getOriginalFilename();
            }
        };

        body.add("file", resource); // 'image'는 서버에서 파일을 받는 파라미터명과 일치해야 합니다.

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 외부 서버의 엔드포인트로 POST 요청을 보냅니다.
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8000/model/checkcal", requestEntity, String.class);

        // 응답으로 받은 JSON 문자열을 반환합니다.
        return response.getBody();
    }

    public String transmitImageToOCR(MultipartFile multipartFile) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        Resource resource = new ByteArrayResource(multipartFile.getBytes()) {
            @Override
            public String getFilename() {
                return multipartFile.getOriginalFilename();
            }
        };

        body.add("file", resource); // 'image'는 서버에서 파일을 받는 파라미터명과 일치해야 합니다.

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 외부 서버의 엔드포인트로 POST 요청을 보냅니다.
        ResponseEntity<String> response =
                restTemplate.postForEntity("http://localhost:8000/model/ocr", requestEntity, String.class);

        // 응답으로 받은 JSON 문자열을 반환합니다.
        return response.getBody();
    }
}
