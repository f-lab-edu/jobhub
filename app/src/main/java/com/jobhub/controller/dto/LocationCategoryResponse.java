package com.jobhub.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class LocationCategoryResponse {
    private final Long id;
    private final String name;
    private final List<LocationSubCategoryResponse> subCategories;
}
