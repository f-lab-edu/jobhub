package com.jobhub.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LocationSubCategoryResponse {
    private Long id;
    private String name;
}
