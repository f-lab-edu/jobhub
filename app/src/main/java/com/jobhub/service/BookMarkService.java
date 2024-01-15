package com.jobhub.service;

import com.jobhub.domain.BookMark;
import com.jobhub.repository.BookMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookMarkService {

    private final BookMarkRepository bookMarkRepository;

    public BookMark updateBookMark(BookMark bookMark) {
        return bookMarkRepository.save(bookMark);
    }
}
