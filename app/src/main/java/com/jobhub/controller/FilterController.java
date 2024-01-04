package com.jobhub.controller;


import com.jobhub.controller.dto.FilterListResponse;
import com.jobhub.controller.dto.JobCategoryResponse;
import com.jobhub.controller.dto.JobSubCategoryResponse;
import com.jobhub.controller.dto.LocationCategoryResponse;
import com.jobhub.controller.dto.LocationSubCategoryResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FilterController {

    @GetMapping("/filters")
    public FilterListResponse getAllFilters() {

        final List<JobSubCategoryResponse> subJoblist = new ArrayList<>();
        final List<LocationSubCategoryResponse> subLocationList = new ArrayList<>();

        JobCategoryResponse jobDummy1 = JobCategoryResponse.builder()
                .id(1L)
                .title("엔지니어")
                .subCategories(subJoblist)
                .totalCount(1)
                .build();

        JobSubCategoryResponse subJobDummy1 = JobSubCategoryResponse.builder()
                .id(1L)
                .title("웹 백엔드 엔지니어")
                .count(1)
                .build();

        subJoblist.add(subJobDummy1);

        LocationCategoryResponse locationDummy1 = LocationCategoryResponse.builder()
                .id(1L)
                .name("대구")
                .subCategories(subLocationList)
                .build();
        LocationSubCategoryResponse subLocationDummy = LocationSubCategoryResponse.builder()
                .id(1L)
                .name("달서구")
                .build();

        subLocationList.add(subLocationDummy);

        return new FilterListResponse(
                List.of(jobDummy1),
                List.of(locationDummy1),
                10);
    }
}
