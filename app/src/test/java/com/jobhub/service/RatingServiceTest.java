package com.jobhub.service;

import com.jobhub.domain.Rating;
import com.jobhub.repository.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    RatingRepository ratingRepository;

    @InjectMocks
    RatingService ratingService;

    @Test
    public void 등록된_채용공고_별점_조회() throws Exception {
        Rating rating = Rating.builder()
                .userId("user123")
                .star(5)
                .comment("좋아요.")
                .build();

        given(ratingRepository.save(any())).willReturn(rating);

        Rating actualRating = ratingService.updateRating(rating);

        assertThat(actualRating).isEqualTo(rating);
    }

}
