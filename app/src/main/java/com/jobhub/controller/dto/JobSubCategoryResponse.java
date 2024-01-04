package com.jobhub.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JobSubCategoryResponse {
    private Long id;
    private String title;
    private int count;
}
