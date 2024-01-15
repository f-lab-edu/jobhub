package com.jobhub.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookMark {
    private String userId;
    private Boolean isBookmark;
    private Recruitment recruitment;
}
