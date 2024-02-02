package com.jobhub.controller.dto;

import com.jobhub.domain.LocationSubCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class LocationSubCategoryRequest {
    private final Long id;
    private final String name;

    public static List<LocationSubCategory> toEntity(List<LocationSubCategoryRequest> locationSubCategoryRequests) {
        List<LocationSubCategory> locationSubCategories = new ArrayList<>();
        for (LocationSubCategoryRequest request : locationSubCategoryRequests) {
            locationSubCategories.add(new LocationSubCategory(request.getId(), request.getName()));
        }
        return locationSubCategories;
    }
}
