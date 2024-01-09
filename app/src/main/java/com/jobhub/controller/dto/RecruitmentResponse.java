package com.jobhub.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class RecruitmentResponse {
    private final Long id;
    private final String url;
    private final String provider;
    private final String title;
    private final String companyName;
    private final String companyAddress;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
}
