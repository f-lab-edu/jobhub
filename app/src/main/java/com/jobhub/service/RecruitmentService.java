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
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;

    public Recruitment saveRecruitment(Recruitment recruitment) {
        return recruitmentRepository.save(recruitment);
    }

    public List<Recruitment> saveAll(List<Recruitment> recruitments) {
        return recruitmentRepository.saveAll(recruitments);
    }

    public List<Recruitment> findAllRecruitment(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, sortBy);
        Page<Recruitment> recruitments = recruitmentRepository.findAll(pageable);
        return recruitments.getContent();
    }

    @Transactional
    public Recruitment updateRecruitmentIfNeeded(Recruitment newRecruitment) {
        Optional<Recruitment> optionalExistingRecruitment =
                recruitmentRepository.findBySignedHash(newRecruitment.getSignedHash());

        return optionalExistingRecruitment
                .map(existingRecruitment -> {
                    updateIfDifferent(existingRecruitment, newRecruitment);
                    return recruitmentRepository.save(existingRecruitment);
                })
                .orElseGet(() -> recruitmentRepository.save(newRecruitment));
    }

    private void updateIfDifferent(Recruitment existingRecruitment, Recruitment newRecruitment) {
        if (!Objects.equals(existingRecruitment.getUrl(), newRecruitment.getUrl())) {
            existingRecruitment.setUrl(newRecruitment.getUrl());
        }
        if (!Objects.equals(existingRecruitment.getProvider(), newRecruitment.getProvider())) {
            existingRecruitment.setProvider(newRecruitment.getProvider());
        }
        if (!Objects.equals(existingRecruitment.getTitle(), newRecruitment.getTitle())) {
            existingRecruitment.setTitle(newRecruitment.getTitle());
        }
        if (!Objects.equals(existingRecruitment.getDepartment(), newRecruitment.getDepartment())) {
            existingRecruitment.setDepartment(newRecruitment.getDepartment());
        }
        if (!Objects.equals(existingRecruitment.getCompanyName(), newRecruitment.getCompanyName())) {
            existingRecruitment.setCompanyName(newRecruitment.getCompanyName());
        }
        if (!Objects.equals(existingRecruitment.getCompanyAddress(), newRecruitment.getCompanyAddress())) {
            existingRecruitment.setCompanyAddress(newRecruitment.getCompanyAddress());
        }
        if (!Objects.equals(existingRecruitment.getStartDate(), newRecruitment.getStartDate())) {
            existingRecruitment.setStartDate(newRecruitment.getStartDate());
        }
        if (!Objects.equals(existingRecruitment.getEndDate(), newRecruitment.getEndDate())) {
            existingRecruitment.setEndDate(newRecruitment.getEndDate());
        }
    }
}
