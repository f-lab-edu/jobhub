package com.jobhub.service;

import com.jobhub.domain.Rating;
import com.jobhub.domain.Recruitment;
import com.jobhub.repository.RatingRepository;
import com.jobhub.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RecruitmentRepository recruitmentRepository;


    public Rating saveRating(String userId, Long recruitmentId, int star, String comment) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new IllegalArgumentException("Recruitment not found with id: " + recruitmentId));
        Rating rating = new Rating(userId, recruitment,  star, comment);
        return ratingRepository.save(rating);
    }
}
