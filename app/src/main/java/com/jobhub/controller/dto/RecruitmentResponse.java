package com.jobhub.controller.dto;

import com.jobhub.domain.Recruitment;
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

    public static RecruitmentResponse fromEntity(final Recruitment recruitment) {
        return new RecruitmentResponse(
                recruitment.getId(),
                recruitment.getUrl(),
                recruitment.getProvider(),
                recruitment.getTitle(),
                recruitment.getCompanyName(),
                recruitment.getCompanyAddress(),
                recruitment.getStartDate(),
                recruitment.getEndDate()
        );

    }

}
