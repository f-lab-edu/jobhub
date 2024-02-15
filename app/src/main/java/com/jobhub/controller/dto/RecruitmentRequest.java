package com.jobhub.controller.dto;

import com.jobhub.domain.Recruitment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class RecruitmentRequest {
    private final String url;
    private final String provider;
    private final String title;
    private final String department;
    private final String career;
    private final String companyName;
    private final String companyAddress;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final String signedHash;


    public static Recruitment toEntity(RecruitmentRequest recruitmentRequest) {
        return Recruitment.builder()
                .url(recruitmentRequest.getUrl())
                .provider(recruitmentRequest.getProvider())
                .title(recruitmentRequest.getTitle())
                .department(recruitmentRequest.getDepartment())
                .companyName(recruitmentRequest.getCompanyName())
                .companyAddress(recruitmentRequest.getCompanyAddress())
                .startDate(recruitmentRequest.getStartDate())
                .endDate(recruitmentRequest.getEndDate())
                .signedHash(recruitmentRequest.signedHash)
                .build();
    }

}
