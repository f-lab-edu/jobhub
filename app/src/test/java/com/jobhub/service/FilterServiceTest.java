
package com.jobhub.service;

import com.jobhub.domain.JobCategory;
import com.jobhub.domain.LocationCategory;
import com.jobhub.repository.JobCategoryRepository;
import com.jobhub.repository.LocationCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FilterServiceTest {

    @Mock
    JobCategoryRepository jobCategoryRepository;

    @Mock
    LocationCategoryRepository locationCategoryRepository;

    @InjectMocks
    FilterService filterService;

    @Test
    public void 직업카테고리_조회() throws Exception {
        //given
        String title = "엔지니어";
        JobCategory parent = JobCategory.builder()
                .title("백엔드 개발자")
                .build();

        JobCategory jobCategory1 = JobCategory.builder()
                .title(title)
                .parent(parent)
                .build();

        JobCategory parent2 = JobCategory.builder()
                .title("프론트엔드 개발자")
                .build();

        JobCategory jobCategory2 = JobCategory.builder()
                .title(title)
                .parent(parent2)
                .build();

        given(this.jobCategoryRepository.findAll()).willReturn(List.of(jobCategory1, jobCategory2));

        //when
        List<JobCategory> actualResponseList = this.filterService.getAllCategories().getJobCategories();

        //then
        assertThat(actualResponseList.size()).isEqualTo(2);
        assertThat(actualResponseList.get(0))
                .extracting("title","parent.title")
                .contains("엔지니어", "백엔드 개발자");

        assertThat(actualResponseList.get(1))
                .extracting("title","parent.title")
                .contains("엔지니어", "프론트엔드 개발자");

    }



    @Test
    public void 지역카테고리_조회() throws Exception {
        //given
        String title = "서울";
        LocationCategory parent1 = LocationCategory.builder()
                .title("송파구")
                .build();

        LocationCategory locationCategory = LocationCategory.builder()
                .title(title)
                .parent(parent1)
                .build();

        LocationCategory parent2 = LocationCategory.builder()
                .title("강남구")
                .build();

        LocationCategory locationCategory2 = LocationCategory.builder()
                .title(title)
                .parent(parent2)
                .build();

        given(this.locationCategoryRepository.findAll()).willReturn(List.of(locationCategory, locationCategory2));

        //when
        List<LocationCategory> actualResponseList = this.filterService.getAllCategories().getLocationCategories();

        //then
        assertThat(actualResponseList.size()).isEqualTo(2);

        assertThat(actualResponseList.get(0))
                .extracting("title","parent.title")
                .contains("서울", "송파구");

        assertThat(actualResponseList.get(1))
                .extracting("title","parent.title")
                .contains("서울", "강남구");
    }

}

