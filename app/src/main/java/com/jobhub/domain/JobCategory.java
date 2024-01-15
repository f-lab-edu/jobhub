package com.jobhub.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class JobCategory {
    private Long id;
    private String title;
    private int totalCount;
    private List<JobSubCategory> subCategories;

    public void addSubCategory(JobSubCategory subCategory) {
        if (subCategories == null) {
            subCategories = new ArrayList<>();
        }
        subCategories.add(subCategory);
    }

}
