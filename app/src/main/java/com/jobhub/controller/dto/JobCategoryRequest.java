package com.jobhub.controller.dto;

import com.jobhub.domain.JobCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class JobCategoryRequest {
    private final Long id;
    private final String title;
    private final int totalCount;
    private final List<JobSubCategoryRequest> subCategories;

    public JobCategory toEntity(JobCategoryRequest categories) {
        return JobCategory.builder()
                .id(categories.getId())
                .title(categories.getTitle())
                .totalCount(categories.getTotalCount())
                .subCategories(JobSubCategoryRequest.toEntity(categories.getSubCategories()))
                .build();
    }
}
