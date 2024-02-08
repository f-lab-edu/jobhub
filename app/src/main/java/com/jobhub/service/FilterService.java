package com.jobhub.service;

import com.jobhub.domain.JobCategory;
import com.jobhub.domain.LocationCategory;
import com.jobhub.repository.JobCategoryRepository;
import com.jobhub.repository.LocationCategoryRepository;
import com.jobhub.service.vo.FilterCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FilterService {
    private final JobCategoryRepository jobCategoryRepository;
    private final LocationCategoryRepository locationCategoryRepository;

    @Transactional(readOnly = true)
    public FilterCategory getAllCategories() {
        List<JobCategory> jobCategories = jobCategoryRepository.findAll();
        List<LocationCategory> locationCategories = locationCategoryRepository.findAll();
        return new FilterCategory(jobCategories, locationCategories);
    }
}
