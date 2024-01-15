package com.jobhub.repository;

import com.jobhub.domain.Recruitment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class RecruitmentRepository {

    private static Map<Long, Recruitment> recruitmentMap = new HashMap<>();

    public Recruitment save (Recruitment recruitment) {
        recruitmentMap.put(recruitment.getId(), recruitment);
        return recruitment;
    }

    public List<Recruitment> findAll(int pageNo, int pageSize, String sortBy) {
        return new ArrayList<>(recruitmentMap.values());
    }
}
