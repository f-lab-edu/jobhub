package com.jobhub.service;

import com.jobhub.domain.Recruitment;
import com.jobhub.repository.RecruitmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RecruitmentServiceTest {

    @Mock
    RecruitmentRepository recruitmentRepository;

  //  @Spy
    @InjectMocks
    RecruitmentService recruitmentService;

    @Test
    public void 채용공고_조회() throws Exception {
        int pageSize = 20;
        int pageNo = 1;
        String sortBy = "startDate";

        Recruitment recruitment1 = Recruitment.builder()
                .id(1L)
                .url("url")
                .provider("wanted")
                .title("당근 백엔드 엔지니어")
                .companyName("당근")
                .companyAddress("서울")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(10))
                .build();

        Recruitment recruitment2 = Recruitment.builder()
                .id(2L)
                .url("url")
                .provider("wanted")
                .title("라인 백엔드 엔지니어")
                .companyName("라인")
                .companyAddress("도쿄")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(10))
                .build();

        List<Recruitment> recruitments = new ArrayList<>();
        recruitments.add(recruitment1);
        recruitments.add(recruitment2);

        given(this.recruitmentRepository.findAll(pageNo, pageSize, sortBy)).willReturn(recruitments);

        List<Recruitment> actualList = this.recruitmentService.findAllRecruitment(pageNo, pageSize, sortBy);

        assertThat(actualList).isEqualTo(recruitments);

    }

}
