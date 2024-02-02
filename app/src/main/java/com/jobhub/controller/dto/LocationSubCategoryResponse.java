package com.jobhub.controller.dto;

import com.jobhub.domain.LocationSubCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class LocationSubCategoryResponse {
    private final Long id;
    private final String name;

    public static List<LocationSubCategoryResponse> fromEntity (List<LocationSubCategory> subCategories) {
            return new ArrayList<>(subCategories.stream()
                    .map(subCategory -> new LocationSubCategoryResponse(
                            subCategory.getId(),
                            subCategory.getName()))
                    .toList());
    }
}
