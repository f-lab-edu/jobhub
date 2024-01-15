package com.jobhub.controller;

import com.jobhub.controller.dto.BookmarkRequest;
import com.jobhub.controller.dto.BookmarkResponse;
import com.jobhub.controller.dto.RecruitmentResponse;
import com.jobhub.domain.BookMark;
import com.jobhub.service.BookMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class BookmarkController {

    private final BookMarkService bookMarkService;

    @PatchMapping("/bookmark/{recruitmentId}")
    public BookmarkResponse updateBookmark(@PathVariable Long recruitmentId, @RequestBody BookmarkRequest bookmarkRequest) {

        BookMark bookMark = bookmarkRequest.toEntity();
        BookMark userBookMark = bookMarkService.updateBookMark(bookMark);

        RecruitmentResponse recruitmentResponse = new RecruitmentResponse(
                userBookMark.getRecruitment().getId(),
                userBookMark.getRecruitment().getUrl(),
                userBookMark.getRecruitment().getProvider(),
                userBookMark.getRecruitment().getTitle(),
                userBookMark.getRecruitment().getCompanyName(),
                userBookMark.getRecruitment().getCompanyAddress(),
                userBookMark.getRecruitment().getStartDate(),
                userBookMark.getRecruitment().getEndDate()

        );

        return new BookmarkResponse(
                userBookMark.getUserId(),
                userBookMark.getIsBookmark(),
                recruitmentResponse
        );
    }

}
