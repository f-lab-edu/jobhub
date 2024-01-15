package com.jobhub.repository;

import com.jobhub.domain.JobCategory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JobCategoryRepository {

    private static final Map<Long, JobCategory> jobCategories = new HashMap<>();

    public JobCategory save(JobCategory jobCategory) {
        jobCategories.put(jobCategory.getId(), jobCategory);
        return jobCategory;
    }

    public List<JobCategory> findAll() {
        return new ArrayList<>(jobCategories.values());
    }
}
