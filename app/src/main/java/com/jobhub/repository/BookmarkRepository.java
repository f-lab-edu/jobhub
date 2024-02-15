package com.jobhub.repository;

import com.jobhub.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Bookmark findByUserId(String userId);
    List<Bookmark> findAllByUserId(String userId);
}
