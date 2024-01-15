package com.jobhub.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Recruitment {

    private Long id;
    private String url;
    private String provider;
    private String title;
    private String companyName;
    private String companyAddress;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
