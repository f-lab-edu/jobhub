package com.jobhub.controller;

import com.jobhub.controller.dto.FilterListResponse;
import com.jobhub.service.FilterService;
import com.jobhub.service.vo.FilterCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class FilterController {

    private final FilterService filterService;

    @GetMapping("/filters")
    public FilterListResponse getAllFilterList() {
        FilterCategory filterCategories = filterService.getAllCategories();
        return FilterListResponse.from(filterCategories);
    }

}
