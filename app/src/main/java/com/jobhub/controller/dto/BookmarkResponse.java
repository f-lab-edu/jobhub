package com.jobhub.controller.dto;

import com.jobhub.domain.Bookmark;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BookmarkResponse {
    private final String userId;
    private final Long recruitmentId;

    public static BookmarkResponse fromEntity(final Bookmark bookmark) {
        return new BookmarkResponse(
                bookmark.getUserId(),
                bookmark.getRecruitment().getId()
        );
    }
}
