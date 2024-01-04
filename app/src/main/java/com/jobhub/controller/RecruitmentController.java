package com.jobhub.controller;

import com.jobhub.controller.dto.RecruitmentResponse;
import com.jobhub.global.wrapper.PageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RecruitmentController {

    @GetMapping("/recruitments")
    public PageResponse<RecruitmentResponse> getRecruitments(@RequestParam String category,
                                                             @RequestParam(required = false) String career,
                                                             @RequestParam(required = false) String locations,
                                                             @RequestParam(required = false) String jobFields,
                                                             @RequestParam(defaultValue = "20") Integer pageSize,
                                                             @RequestParam(defaultValue = "1") Integer pageNo,
                                                             @RequestParam(defaultValue = "start_date") String sortBy) {
        RecruitmentResponse dummy1 = RecruitmentResponse.builder()
                .id(1L)
                .url("url")
                .provider("wanted")
                .title("당근 백엔드 엔지니어")
                .companyName("당근")
                .companyAddress("서울")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(10))
                .build();

        RecruitmentResponse dummy2 = RecruitmentResponse.builder()
                .id(2L)
                .url("url")
                .provider("wanted")
                .title("라인 백엔드 엔지니어")
                .companyName("라인")
                .companyAddress("도쿄")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(10))
                .build();
        return new PageResponse<>(2, List.of(dummy1, dummy2));
    }

}
