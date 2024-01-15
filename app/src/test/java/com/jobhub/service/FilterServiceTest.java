package com.jobhub.service;

import com.jobhub.domain.JobCategory;
import com.jobhub.domain.JobSubCategory;
import com.jobhub.domain.LocationCategory;
import com.jobhub.domain.LocationSubCategory;
import com.jobhub.repository.JobCategoryRepository;
import com.jobhub.repository.LocationCategoryRepository;
import org.assertj.core.util.Lists;
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

        JobSubCategory expectJobSubCategory = new JobSubCategory(1L, "웹 백엔드 엔지니어", 1);
        JobCategory expectJobCategory = JobCategory.builder()
                .id(1L)
                .title("엔지니어")
                .subCategories(Lists.newArrayList(expectJobSubCategory))
                .totalCount(10)
                .build();

        given(jobCategoryRepository.findAll()).willReturn(List.of(expectJobCategory));

        List<JobCategory> actualJobCategoryList = filterService.findAllJobCategory();

        assertThat(actualJobCategoryList).isEqualTo(List.of(expectJobCategory));


    }

    @Test
    public void 지역카테고리_조회() throws Exception {

        LocationSubCategory locationSubCategory = new LocationSubCategory(1L, "달서구");
        LocationCategory locationCategory = LocationCategory.builder()
                .id(1L)
                .name("대구")
                .subCategories(Lists.newArrayList(locationSubCategory))
                .build();

        given(locationCategoryRepository.findAll()).willReturn(List.of(locationCategory));

        List<LocationCategory> actualLocationCategoryList = filterService.findAllLocationCategory();

        assertThat(actualLocationCategoryList).isEqualTo(List.of(locationCategory));

    }

}
