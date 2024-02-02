package com.jobhub.service;

import com.jobhub.domain.JobCategory;
import com.jobhub.domain.LocationCategory;
import com.jobhub.repository.JobCategoryRepository;
import com.jobhub.repository.LocationCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FilterService {
    private final JobCategoryRepository jobCategoryRepository;
    private final LocationCategoryRepository locationCategoryRepository;


    public List<JobCategory> findAllJobCategory() {
        return jobCategoryRepository.findAll();
    }

    public List<LocationCategory> findAllLocationCategory() {
        return locationCategoryRepository.findAll();
    }

    public JobCategory saveJobCategory(JobCategory jobCategory) {
        return jobCategoryRepository.save(jobCategory);
    }


    public LocationCategory saveLocationCategory(LocationCategory locationCategory) {
        return locationCategoryRepository.save(locationCategory);
    }

}
