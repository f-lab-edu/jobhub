package com.jobhub.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RecruitmentRequest {
    private final Long id;
    private final String url;
    private final String provider;
    private final String title;
    private final String career;
    private final String companyName;
    private final String companyAddress;
    private final String startDate;
    private final String endDate;
    private final String signedHash;

    @Override
    public String toString() {
        return "RecruitmentRequest{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", provider='" + provider + '\'' +
                ", title='" + title + '\'' +
                ", career='" + career + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", signedHash='" + signedHash + '\'' +
                '}';
    }
}
