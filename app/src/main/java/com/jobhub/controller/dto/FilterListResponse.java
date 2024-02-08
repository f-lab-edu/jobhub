package com.jobhub.controller.dto;

import com.jobhub.service.vo.FilterCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class FilterListResponse {
    private final List<JobCategoryResponse> categories;
    private final List<LocationCategoryResponse> locations;
    private final int maxCareer;

    public static FilterListResponse from(FilterCategory filterCategories) {
        List<JobCategoryResponse> jobCategoryResponses = JobCategoryResponse.list(filterCategories.getJobCategories());
        List<LocationCategoryResponse> locationCategoriesResponse = filterCategories.getLocationCategories().stream()
                .map(LocationCategoryResponse::fromEntity)
                .toList();
        return new FilterListResponse(jobCategoryResponses, locationCategoriesResponse, 20);
    }
}
