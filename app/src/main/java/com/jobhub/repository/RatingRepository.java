package com.jobhub.repository;

import com.jobhub.domain.Rating;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class RatingRepository {
    private static final Map<String, Rating> rating = new HashMap<>();

    public Rating save(Rating userRating) {
        rating.put(userRating.getUserId(), userRating);
        return userRating;
    }
}
