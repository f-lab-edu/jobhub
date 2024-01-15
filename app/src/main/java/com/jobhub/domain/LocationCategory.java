package com.jobhub.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class LocationCategory {
    private Long id;
    private String name;
    private List<LocationSubCategory> subCategories;

    public void addLocationSubcategory(LocationSubCategory subCategory) {
        if (subCategories == null) {
            subCategories = new ArrayList<>();
        }
        subCategories.add(subCategory);
    }
}
