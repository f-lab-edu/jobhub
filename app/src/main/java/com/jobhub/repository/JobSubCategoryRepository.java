package com.jobhub.repository;

import com.jobhub.domain.JobSubCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobSubCategoryRepository {
    static final Map<String, JobSubCategory> jobSubCategories = new HashMap<>();

    public JobSubCategoryRepository() {
        JobSubCategory jobSubCategory = new JobSubCategory(1L, "백엔드 엔지니어", 1);
        jobSubCategories.put(jobSubCategory.getTitle(), jobSubCategory);
    }

    public List<JobSubCategory> findAll() {
        return new ArrayList<>(jobSubCategories.values());
    }
}
