package com.jobhub.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JobSubCategoryResponse {
    private final String title;
    private final int count;
}
