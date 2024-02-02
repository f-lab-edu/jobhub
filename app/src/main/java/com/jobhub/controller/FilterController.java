package com.jobhub.controller;


import com.jobhub.controller.dto.FilterListResponse;
import com.jobhub.controller.dto.JobCategoryRequest;
import com.jobhub.controller.dto.JobCategoryResponse;
import com.jobhub.controller.dto.JobSubCategoryResponse;
import com.jobhub.controller.dto.LocationCategoryRequest;
import com.jobhub.controller.dto.LocationCategoryResponse;
import com.jobhub.controller.dto.LocationSubCategoryResponse;
import com.jobhub.domain.JobCategory;
import com.jobhub.domain.LocationCategory;
import com.jobhub.service.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/filters/job")
    public ResponseEntity<JobCategoryResponse> createFilter(@RequestBody JobCategoryRequest jobCategoryRequest) {
        JobCategory jobCategory = jobCategoryRequest.toEntity(jobCategoryRequest);
        filterService.saveJobCategory(jobCategory);
        JobCategoryResponse jobCategoryResponse = JobCategoryResponse.fromEntity(filterService.saveJobCategory(jobCategory));
        return new ResponseEntity<>(jobCategoryResponse,HttpStatus.CREATED);

    }
    @PostMapping("/filters/location")
    public ResponseEntity<LocationCategoryResponse> createFilter(@RequestBody LocationCategoryRequest locationCategoryRequest) {
        LocationCategory locationCategory = locationCategoryRequest.toEntity(locationCategoryRequest);
        filterService.saveLocationCategory(locationCategory);
        LocationCategoryResponse locationCategoryResponse = LocationCategoryResponse.fromEntity(filterService.saveLocationCategory(locationCategory));

        return new ResponseEntity<>(locationCategoryResponse,HttpStatus.CREATED);

    }

}
