package com.jobhub.service;

import com.jobhub.domain.Rating;
import com.jobhub.domain.Recruitment;
import com.jobhub.repository.RatingRepository;
import com.jobhub.repository.RecruitmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    RecruitmentRepository recruitmentRepository;

    @Mock
    RatingRepository ratingRepository;

    @InjectMocks
    RatingService ratingService;

    @Test
    public void 별점_저장() throws Exception {

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

        Rating rating = Rating.builder()
                .userId("user123")
                .star(5)
                .comment("좋아요.")
                .build();

        given(this.recruitmentRepository.findById(any())).willReturn(java.util.Optional.of(recruitment));
        given(ratingRepository.save(any())).willReturn(rating);

        Rating actualRating = ratingService.saveRating( "user123", 1L,5, "좋아요.");

        assertThat(actualRating).isEqualTo(rating);
    }


}
