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

        JobCategoryResponse jobDummy1 = new JobCategoryResponse(1L, "엔지니어", 1, subJoblist);
        JobSubCategoryResponse subJobDummy1 = new JobSubCategoryResponse(1L, "웹 백엔드 엔지니어", 1);

        subJoblist.add(subJobDummy1);

        LocationCategoryResponse locationDummy1 = new LocationCategoryResponse(1L, "대구", subLocationList);
        LocationSubCategoryResponse subLocationDummy = new LocationSubCategoryResponse(1L, "달서구");

        subLocationList.add(subLocationDummy);

        return new FilterListResponse(
                List.of(jobDummy1),
                List.of(locationDummy1),
                10);
    }
}
