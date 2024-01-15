package com.jobhub.controller.dto;

import com.jobhub.domain.BookMark;
import com.jobhub.domain.Recruitment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BookmarkRequest {
    private final String userId;
    private final Boolean isBookmark;
    private final Recruitment recruitment;

    public BookMark toEntity() {
       return BookMark.builder()
                .userId(userId)
                .isBookmark(isBookmark)
                .recruitment(recruitment)
                .build();
    }
}
