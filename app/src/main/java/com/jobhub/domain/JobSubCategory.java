package com.jobhub.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JobSubCategory {
    private Long id;
    private String title;
    private int count;
}
