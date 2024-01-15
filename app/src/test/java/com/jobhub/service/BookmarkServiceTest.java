package com.jobhub.service;

import com.jobhub.domain.BookMark;
import com.jobhub.domain.Recruitment;
import com.jobhub.repository.BookMarkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class BookmarkServiceTest {

    @Mock
    BookMarkRepository bookMarkRepository;

    @InjectMocks
    BookMarkService bookmarkService;

    @Test
    public void 즐겨찾기_조회() throws Exception {

        Recruitment recruitment = Recruitment.builder()
                .id(3L)
                .url("url")
                .provider("jobKorea")
                .title("쿠팡 프론트엔드 개발자")
                .companyName("쿠팡")
                .companyAddress("서울")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(10))
                .build();

        BookMark bookMark = BookMark.builder()
                .userId("user123")
                .isBookmark(true)
                .recruitment(recruitment)
                .build();

        given(this.bookMarkRepository.save(any())).willReturn(bookMark);

        BookMark actualBookMark = this.bookmarkService.updateBookMark(bookMark);

        assertThat(actualBookMark).isEqualTo(bookMark);

    }

}
