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
    private final List<JobSubCategoryResponse> subCategories;
    private final int totalCount;

    public static List<JobCategoryResponse> list(List<JobCategory> categories) {
        return categories.stream()
                .map(category -> new JobCategoryResponse(
                        category.getId(),
                        category.getTitle(),
                        category.getSubCategories().stream()
                                .map(subCategory -> new JobSubCategoryResponse(
                                        subCategory.getId(),
                                        subCategory.getTitle(),
                                        subCategory.getSubCategories().size()
                                )).toList(),
                        category.getSubCategories().size()
                )).toList();
    }
}
