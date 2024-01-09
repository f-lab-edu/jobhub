package com.jobhub.global.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PageResponse<T> {
    private int total;
    private List<T> contents;
}
