package com.jobhub.controller.dto;

import com.jobhub.domain.JobSubCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class JobSubCategoryRequest {
    private final Long id;
    private final String title;
    private final int count;

    public static List<JobSubCategory> toEntity(List<JobSubCategoryRequest> jobSubCategoryRequests) {
        List<JobSubCategory> jobSubCategories = new ArrayList<>();
        for (JobSubCategoryRequest request : jobSubCategoryRequests) {
            jobSubCategories.add(new JobSubCategory(request.getId(), request.getTitle(), request.getCount()));
        }
        return jobSubCategories;
    }
}
