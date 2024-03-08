

package com.jobhub.service;

import com.jobhub.domain.Recruitment;
import com.jobhub.repository.RecruitmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RecruitmentServiceTest {

    @Mock
    RecruitmentRepository recruitmentRepository;

    @InjectMocks
    RecruitmentService recruitmentService;

    @Test
    public void 채용공고_조회() throws Exception {

        int pageSize = 20;
        int pageNo = 1;
        String sortBy = "startDate";

        Recruitment recruitment1 =
                createRecruitment(
                        "wanted",
                        "당근 백엔드 엔지니어",
                        "당근",
                        "서울"
                );

        Recruitment recruitment2 =
                createRecruitment(
                        "jobKorea",
                        "쿠팡 프론트엔드 개발자",
                        "쿠팡",
                        "서울"
                );

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, sortBy);
        given(this.recruitmentRepository.findAll(pageable)).willReturn(new PageImpl<>(List.of(recruitment1, recruitment2)));

        List<Recruitment> actualResponseList = this.recruitmentService.findAllRecruitment(pageNo, pageSize, sortBy);

        assertThat(actualResponseList.size()).isEqualTo(2);
        assertThat(actualResponseList.get(0))
                .extracting("provider","title", "companyName", "companyAddress")
                .contains("wanted", "당근 백엔드 엔지니어", "당근", "서울");

        assertThat(actualResponseList.get(1)).extracting("provider","title", "companyName", "companyAddress")
                .contains("jobKorea", "쿠팡 프론트엔드 개발자", "쿠팡", "서울");



    }

    @Test
    public void 채용공고_저장() throws Exception {
           //when
            Recruitment recruitment1 =
                    createRecruitment(
                            "wanted",
                            "당근 백엔드 엔지니어",
                            "당근",
                            "서울"
                    );

            Recruitment recruitment2 =
                    createRecruitment(
                            "jobKorea",
                            "쿠팡 프론트엔드 개발자",
                            "쿠팡",
                            "서울"
                    );

            //given
            given(this.recruitmentRepository.saveAll(List.of(recruitment1, recruitment2)))
                    .willReturn(List.of(recruitment1, recruitment2));

            //then
            List<Recruitment> actualResponseList = this.recruitmentService.saveAll(List.of(recruitment1, recruitment2));

            assertThat(actualResponseList.get(0))
                    .extracting("provider","title", "companyName", "companyAddress")
                    .contains("wanted", "당근 백엔드 엔지니어", "당근", "서울");

            assertThat(actualResponseList.get(1))
                    .extracting("provider","title", "companyName", "companyAddress")
                    .contains("jobKorea", "쿠팡 프론트엔드 개발자", "쿠팡", "서울");


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
