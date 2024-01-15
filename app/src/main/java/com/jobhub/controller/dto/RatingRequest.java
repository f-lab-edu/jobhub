package com.jobhub.controller.dto;

import com.jobhub.domain.Rating;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RatingRequest {
    private final String userId;
    private final int star;
    private final String comment;

    public Rating toEntity() {
        return Rating.builder()
                .userId(userId)
                .star(star)
                .comment(comment)
                .build();
    }
}
