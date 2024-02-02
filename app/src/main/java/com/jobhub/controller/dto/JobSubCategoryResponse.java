package com.jobhub.controller.dto;

import com.jobhub.domain.JobSubCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class JobSubCategoryResponse {
    private final Long id;
    private final String title;
    private final int count;

    public static List<JobSubCategoryResponse> fromEntity(List<JobSubCategory> jobSubCategory) {
        return new ArrayList<>(jobSubCategory.stream()
                .map(subCategory -> new JobSubCategoryResponse(
                        subCategory.getId(),
                        subCategory.getTitle(),
                        subCategory.getCount()))
                .toList());
    }
}
