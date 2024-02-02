package com.jobhub.controller.dto;

import com.jobhub.domain.JobCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class JobCategoryResponse {
    private final Long id;
    private final String title;
    private final int totalCount;
    private final List<JobSubCategoryResponse> subCategories;

    public static JobCategoryResponse fromEntity(JobCategory jobCategory) {
        return new JobCategoryResponse(
            jobCategory.getId(),
            jobCategory.getTitle(),
            jobCategory.getTotalCount(),
            JobSubCategoryResponse.fromEntity(jobCategory.getSubCategories())
        );
    }

}
