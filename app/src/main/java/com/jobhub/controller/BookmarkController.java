package com.jobhub.controller;

import com.jobhub.controller.dto.BookmarkRequest;
import com.jobhub.controller.dto.BookmarkResponse;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BookmarkController {

    @PatchMapping("/bookmark/{recruitmentId}")
    public BookmarkResponse updateBookmark(@PathVariable Long recruitmentId, @RequestBody BookmarkRequest bookmark) {
        return new BookmarkResponse(bookmark.getUserId(), bookmark.getIsBookmark());
    }

}
