package com.jobhub.controller;

import com.jobhub.controller.dto.RecruitmentRequest;
import com.jobhub.controller.dto.RecruitmentResponse;
import com.jobhub.domain.Recruitment;
import com.jobhub.global.wrapper.PageResponse;
import com.jobhub.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @GetMapping("/recruitments")
    public PageResponse<RecruitmentResponse> getRecruitments(@RequestParam String category,
                                                             @RequestParam(required = false) String career,
                                                             @RequestParam(required = false) String locations,
                                                             @RequestParam(required = false) String jobFields,
                                                             @RequestParam(defaultValue = "20") Integer pageSize,
                                                             @RequestParam(defaultValue = "1") Integer pageNo,
                                                             @RequestParam(defaultValue = "start_date") String sortBy) {

        List<RecruitmentResponse> responseList = recruitmentService.findAllRecruitment(pageNo, pageSize, sortBy).stream()
                .map(recruitment -> new RecruitmentResponse(
                        recruitment.getId(),
                        recruitment.getUrl(),
                        recruitment.getProvider(),
                        recruitment.getTitle(),
                        recruitment.getCompanyName(),
                        recruitment.getCompanyAddress(),
                        recruitment.getStartDate(),
                        recruitment.getEndDate()
                ))
                .toList();

        return new PageResponse<>(responseList.size(), responseList);
    }


    @PostMapping("/recruitments")
    public ResponseEntity<RecruitmentResponse> createRecruitment(@RequestBody RecruitmentRequest recruitmentRequest) {
        Recruitment recruitment = recruitmentRequest.toEntity(recruitmentRequest);
        Recruitment response = recruitmentService.saveRecruitment(recruitment);
        RecruitmentResponse responseDTO = RecruitmentResponse.fromEntity(response);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/recruitments/update")
    public ResponseEntity<RecruitmentResponse> updateRecruitment(@RequestBody RecruitmentRequest recruitmentRequest) {
        Recruitment recruitment = RecruitmentRequest.toEntity(recruitmentRequest);
        Recruitment response = recruitmentService.updateRecruitmentIfNeeded(recruitment);
        RecruitmentResponse responseDTO = RecruitmentResponse.fromEntity(response);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

}
