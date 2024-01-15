package com.jobhub.controller;

import com.jobhub.controller.dto.RatingRequest;
import com.jobhub.controller.dto.RatingResponse;
import com.jobhub.domain.Rating;
import com.jobhub.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/{recruitmentId}/rating")
    public RatingResponse addRecruitmentRating(@PathVariable Long recruitmentId, @RequestBody RatingRequest ratingRequest) {

        Rating rating = ratingRequest.toEntity();
        Rating userRating = ratingService.updateRating(rating);

        return new RatingResponse(
                userRating.getUserId(),
                userRating.getStar(),
                userRating.getComment()
        );
    }
}
