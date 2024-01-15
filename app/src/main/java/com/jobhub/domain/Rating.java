package com.jobhub.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Rating {
    private String userId;
    private int star;
    private String comment;
}
