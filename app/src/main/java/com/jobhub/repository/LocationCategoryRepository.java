package com.jobhub.repository;

import com.jobhub.domain.LocationCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationCategoryRepository extends JpaRepository<LocationCategory, Long> {


}
