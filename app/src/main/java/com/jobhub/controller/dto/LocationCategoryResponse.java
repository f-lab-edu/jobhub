package com.jobhub.controller.dto;

import com.jobhub.domain.LocationCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class LocationCategoryResponse {
    private final Long id;
    private final String name;
    private final List<LocationSubCategoryResponse> subCategories;

    public static LocationCategoryResponse fromEntity(LocationCategory locationCategory) {
        return new LocationCategoryResponse(
            locationCategory.getId(),
            locationCategory.getName(),
            LocationSubCategoryResponse.fromEntity(locationCategory.getSubCategories())
        );
    }
}
