package com.jobhub.controller;

import com.jobhub.controller.dto.BookmarkRequest;
import com.jobhub.controller.dto.BookmarkResponse;
import com.jobhub.domain.Bookmark;
import com.jobhub.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PatchMapping("/bookmark")
    public BookmarkResponse updateBookmark(@RequestBody BookmarkRequest bookmarkRequest) {
        Bookmark bookmark = bookmarkService.saveBookmark(bookmarkRequest.getUserId()
                , bookmarkRequest.getRecruitmentId());
        return BookmarkResponse.fromEntity(bookmark);
    }

}
