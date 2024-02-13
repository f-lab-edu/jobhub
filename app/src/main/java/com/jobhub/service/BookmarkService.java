package com.jobhub.service;

import com.jobhub.domain.Bookmark;
import com.jobhub.domain.Recruitment;
import com.jobhub.repository.BookmarkRepository;
import com.jobhub.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookmarkService {

    private final RecruitmentRepository recruitmentRepository;
    private final BookmarkRepository bookmarkRepository;

    public List<Bookmark> findBookmarks(String userId) {
        return bookmarkRepository.findAllByUserId(userId);
    }

    public Bookmark saveBookmark(String userId, Long recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new IllegalArgumentException("Recruitment not found with id: " + recruitmentId));

        Bookmark bookmark = new Bookmark(userId, recruitment);
        return bookmarkRepository.save(bookmark);
    }
}
