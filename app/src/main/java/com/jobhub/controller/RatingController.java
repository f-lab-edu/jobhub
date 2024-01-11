package com.jobhub.controller;

import com.jobhub.controller.dto.RatingRequest;
import com.jobhub.controller.dto.RatingResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class RatingController {
    @PostMapping("/{recruitmentId}/rating")
    public RatingResponse addRecruitmentRating(@PathVariable Long recruitmentId, @RequestBody RatingRequest rating) {
        return new RatingResponse(
                rating.getUserId(),
                rating.getStar(),
                rating.getComment()
        );

    }

}
