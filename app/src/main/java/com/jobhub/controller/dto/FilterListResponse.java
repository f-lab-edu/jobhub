package com.jobhub.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@AllArgsConstructor
@Getter
public class FilterListResponse {
    private List<JobCategoryResponse> categories;
    private List<LocationCategoryResponse> locations;
    private int maxCareer;
}
