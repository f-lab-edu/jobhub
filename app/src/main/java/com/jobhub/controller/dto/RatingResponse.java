package com.jobhub.controller.dto;

import com.jobhub.domain.Rating;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RatingResponse {
    private final String userId;
    private final Long recruitmentId;
    private final int star;
    private final String comment;

    public static RatingResponse fromEntity(final Rating rating) {
        return new RatingResponse(
                rating.getUserId(),
                rating.getRecruitment().getId(),
                rating.getStar(),
                rating.getComment()
        );
    }
}
