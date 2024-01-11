package com.jobhub.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LocationSubCategoryResponse {
    private final Long id;
    private final String name;
}
