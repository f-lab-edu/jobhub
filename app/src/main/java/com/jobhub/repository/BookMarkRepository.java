package com.jobhub.repository;

import com.jobhub.domain.BookMark;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BookMarkRepository {
    private static final Map<String, BookMark> userBookMark = new HashMap<>();

    public BookMark save(BookMark bookMark) {
        userBookMark.put(bookMark.getUserId(), bookMark);
        return userBookMark.get(bookMark.getUserId());
    }
}
