package com.jobhub.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class FilterListResponse {
    private final List<JobCategoryResponse> categories;
    private final List<LocationCategoryResponse> locations;
    private final int maxCareer;
}
