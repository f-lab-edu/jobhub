package com.jobhub.service;

import com.jobhub.domain.Bookmark;
import com.jobhub.domain.Recruitment;
import com.jobhub.repository.BookmarkRepository;
import com.jobhub.repository.RecruitmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class BookmarkServiceTest {

    @Mock
    BookmarkRepository bookMarkRepository;

    @Mock
    RecruitmentRepository recruitmentRepository;

    @InjectMocks
    BookmarkService bookmarkService;


    @Test
    public void 즐겨찾기_생성() throws Exception {
        // Given
        Recruitment recruitment = Recruitment.builder()
                .url("url")
                .provider("jobKorea")
                .title("쿠팡 프론트엔드 개발자")
                .companyName("쿠팡")
                .companyAddress("서울")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(10))
                .signedHash("hash")
                .build();

        Bookmark bookMark = Bookmark.builder()
                .userId("user123")
                .recruitment(recruitment)
                .build();

        given(this.recruitmentRepository.findById(any())).willReturn(java.util.Optional.of(recruitment));
        given(this.bookMarkRepository.save(any())).willReturn(bookMark);

        // When
        Bookmark actualBookMark = bookmarkService.saveBookmark("user123", 1L);

        // Then
        assertThat(actualBookMark).isEqualTo(bookMark);

    }

    @Test
    public void 해당_아이디_즐겨찾기_조회() throws Exception {

        Recruitment recruitment = Recruitment.builder()
                .url("url")
                .provider("jobKorea")
                .title("쿠팡 프론트엔드 개발자")
                .department("엔지니어")
                .companyName("쿠팡")
                .companyAddress("서울")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(10))
                .signedHash("hash")
                .build();

        Bookmark bookMark = Bookmark.builder()
                .userId("user123")
                .recruitment(recruitment)
                .build();

        given(this.bookMarkRepository.findAllByUserId(anyString())).willReturn(List.of(bookMark));

        List<Bookmark> actualBookMark = bookmarkService.findBookmarks("user123");

        assertThat(actualBookMark).isEqualTo(List.of(bookMark));

    }

}
