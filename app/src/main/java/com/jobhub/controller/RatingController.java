package com.jobhub.controller;

import com.jobhub.controller.dto.RatingRequest;
import com.jobhub.controller.dto.RatingResponse;
import com.jobhub.domain.Rating;
import com.jobhub.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/rating")
    public RatingResponse addRecruitmentRating(@RequestBody RatingRequest ratingRequest) {
        Rating rating = ratingService.saveRating(ratingRequest.getUserId(), ratingRequest.getRecruitmentId(), ratingRequest.getStar(), ratingRequest.getComment());
        return RatingResponse.fromEntity(rating);
    }
}
