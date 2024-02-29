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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

        Recruitment recruitment = createRecruitment(
                "wanted",
                "당근 백엔드 엔지니어",
                "당근",
                "서울"
        );


        Rating rating = Rating.builder()
                .userId("user123")
                .star(5)
                .recruitment(recruitment)
                .comment("좋아요.")
                .build();

        given(this.recruitmentRepository.findById(any())).willReturn(Optional.of(recruitment));
        given(ratingRepository.save(any())).willReturn(rating);

        Rating actualRating = ratingService.saveRating( "user123", 1L,5, "좋아요.");

        assertThat(actualRating).isEqualTo(rating);
    }

    @Test
    public void 존재하지_않는_채용공고에_별점_추가() throws Exception {
        Long recruitmentId = 9999L;
        given(this.recruitmentRepository.findById(any())).willReturn(Optional.empty());

        assertThatThrownBy(() -> ratingService.saveRating( "user123", recruitmentId,5, "좋아요."))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Recruitment not found with id: " + recruitmentId);
    }

    private Recruitment createRecruitment(String provider, String title, String companyName, String companyAddress) {
        return Recruitment.builder()
                .url("url")
                .provider(provider)
                .title(title)
                .companyName(companyName)
                .companyAddress(companyAddress)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(10))
                .signedHash("hash")
                .build();
    }


}
