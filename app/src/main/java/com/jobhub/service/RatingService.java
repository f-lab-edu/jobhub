package com.jobhub.service;

import com.jobhub.domain.Rating;
import com.jobhub.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RatingService {
    private final RatingRepository ratingRepository;

    public Rating updateRating(Rating rating) {
        return ratingRepository.save(rating);
    }
}
