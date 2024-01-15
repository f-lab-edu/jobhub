package com.jobhub.repository;

import com.jobhub.domain.LocationCategory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LocationCategoryRepository {

    private static final Map<Long, LocationCategory> locationCategories = new HashMap<>();

    public LocationCategory save(LocationCategory locationCategory) {
        locationCategories.put(locationCategory.getId(), locationCategory);
        return locationCategory;
    }

    public List<LocationCategory> findAll() {
        return new ArrayList<>(locationCategories.values());
    }
}
