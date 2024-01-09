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
        RecruitmentResponse dummy1 = new RecruitmentResponse(1L, "url", "wanted"
                , "당근 백엔드 엔지니어", "당근", "서울"
                , LocalDateTime.now(), LocalDateTime.now().plusDays(10));


        RecruitmentResponse dummy2 = new RecruitmentResponse(2L, "url", "wanted"
                , "라인 백엔드 엔지니어", "라인", "도쿄"
                , LocalDateTime.now(), LocalDateTime.now().plusDays(10));

        return new PageResponse<>(2, List.of(dummy1, dummy2));
    }

}
