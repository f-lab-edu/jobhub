package com.jobhub.controller.dto;

import com.jobhub.domain.Bookmark;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BookmarkResponse {
    private final Long id;
    private final String userId;
    private final Long recruitmentId;

    public static BookmarkResponse fromEntity(final Bookmark bookmark) {
        return new BookmarkResponse(
                bookmark.getId(),
                bookmark.getUserId(),
                bookmark.getRecruitment().getId()
        );
    }
}
