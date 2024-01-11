package com.jobhub.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BookmarkResponse {
    private final String userId;
    private final Boolean isBookmark;
}
