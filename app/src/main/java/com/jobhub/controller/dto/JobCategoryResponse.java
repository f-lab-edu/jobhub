package com.jobhub.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class JobCategoryResponse {
    private final Long id;
    private final String title;
    private final int totalCount;
    private final List<JobSubCategoryResponse> subCategories;
}
