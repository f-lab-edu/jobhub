package com.jobhub.service.vo;

import com.jobhub.domain.JobCategory;
import com.jobhub.domain.LocationCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class FilterCategory {
    private final List<JobCategory> jobCategories;
    private final List<LocationCategory> locationCategories;
}
