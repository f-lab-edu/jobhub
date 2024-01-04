package com.jobhub.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CareerCategoryResponse {
    private Long id;
    private String career;
}
