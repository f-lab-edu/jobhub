package com.jobhub.controller;


import com.jobhub.controller.dto.FilterListResponse;
import com.jobhub.controller.dto.JobCategoryResponse;
import com.jobhub.controller.dto.JobSubCategoryResponse;
import com.jobhub.controller.dto.LocationCategoryResponse;
import com.jobhub.controller.dto.LocationSubCategoryResponse;
import com.jobhub.service.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class FilterController {

    private final FilterService filterService;

    @GetMapping("/filters")
    public FilterListResponse getAllFilterList() {

        List<JobCategoryResponse> JobCategoriesResponse = filterService.findAllJobCategory().stream()
                .map(category -> new JobCategoryResponse(
                        category.getId(),
                        category.getTitle(),
                        category.getTotalCount(),
                        category.getSubCategories().stream()
                                .map(subCategory -> new JobSubCategoryResponse(
                                        subCategory.getId(),
                                        subCategory.getTitle(),
                                        subCategory.getCount()
                                )).toList()
                )).toList();

        List<LocationCategoryResponse> locationCategoriesResponse = filterService.findAllLocationCategory().stream()
                .map(location -> new LocationCategoryResponse(
                        location.getId(),
                        location.getName(),
                        location.getSubCategories().stream()
                                .map(subCategory -> new LocationSubCategoryResponse(
                                        subCategory.getId(),
                                        subCategory.getName()
                                )).toList()
                )).toList();
        int maxCareer = 10;

        return new FilterListResponse(JobCategoriesResponse, locationCategoriesResponse, maxCareer);
    }
}
