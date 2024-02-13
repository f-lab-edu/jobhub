package com.jobhub.service;

import com.jobhub.domain.Recruitment;
import com.jobhub.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;

    public Recruitment saveRecruitment(Recruitment recruitment){
        return recruitmentRepository.save(recruitment);
    }

    public List<Recruitment> findAllRecruitment(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, sortBy);
        Page<Recruitment> recruitments = recruitmentRepository.findAll(pageable);
        return recruitments.getContent();
    }

    @Transactional
    public Recruitment updateRecruitmentIfNeeded(Recruitment newRecruitment) {
        Recruitment recruitment
                = recruitmentRepository.findBySignedHash(newRecruitment.getSignedHash())
                .orElse(newRecruitment);
        recruitment.change(newRecruitment);
        return recruitmentRepository.save(newRecruitment);
    }
}
