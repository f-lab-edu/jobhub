package com.jobhub.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class JobCategoryResponse {
    private Long id;
    private String title;
    private int totalCount;
    private List<JobSubCategoryResponse> subCategories;
}
