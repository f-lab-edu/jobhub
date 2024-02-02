package com.jobhub.controller.dto;

import com.jobhub.domain.LocationCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class LocationCategoryRequest {
    private final Long id;
    private final String name;
    private final List<LocationSubCategoryRequest> subCategories;

    public LocationCategory toEntity(LocationCategoryRequest locations) {
        return LocationCategory.builder()
                .id(locations.getId())
                .name(locations.getName())
                .subCategories(LocationSubCategoryRequest.toEntity(locations.getSubCategories()))
                .build();
    }

}
