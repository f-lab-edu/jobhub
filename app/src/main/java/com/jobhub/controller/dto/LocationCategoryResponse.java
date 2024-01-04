package com.jobhub.controller.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Builder
@Getter
public class LocationCategoryResponse {
    private Long id;
    private String name;
    private List<LocationSubCategoryResponse> subCategories;
}
