package com.jobhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobhub.controller.dto.FilterListResponse;
import com.jobhub.controller.dto.JobCategoryResponse;
import com.jobhub.controller.dto.LocationCategoryResponse;
import com.jobhub.domain.JobCategory;
import com.jobhub.domain.JobSubCategory;
import com.jobhub.domain.LocationCategory;
import com.jobhub.domain.LocationSubCategory;
import com.jobhub.repository.JobCategoryRepository;
import com.jobhub.repository.LocationCategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    @Autowired
    private LocationCategoryRepository locationCategoryRepository;


    @Test
    void 각_필터목록_값들이_일치하는지_확인() throws Exception {

        //given
        JobSubCategory jobSubCategory = new JobSubCategory(1L, "웹 백엔드 엔지니어", 1);
        List<JobCategory> jobCategoriesResponse=
                List.of(JobCategory.builder()
                        .id(1L)
                        .title("엔지니어")
                        .totalCount(10)
                        .subCategories(List.of(jobSubCategory))
                        .build());


        LocationSubCategory locationSubCategory = new LocationSubCategory(1L, "달서구");
        List<LocationCategory> locationCategory = List.of(LocationCategory.builder()
                .id(1L)
                .name("대구")
                .subCategories(List.of(locationSubCategory))
                .build());

        jobCategoryRepository.save(jobCategoriesResponse.get(0));
        locationCategoryRepository.save(locationCategory.get(0));

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/filters"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));


        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        FilterListResponse filterListResponse = objectMapper.readValue(contentAsString, FilterListResponse.class);

        JobCategoryResponse jobCategoryResponses = filterListResponse.getCategories().get(0);
        LocationCategoryResponse Location = filterListResponse.getLocations().get(0);
        int maxCareer = filterListResponse.getMaxCareer();

        //then
        assertThat(jobCategoryResponses.getTitle()).isEqualTo("엔지니어");
        assertThat(Location.getName()).isEqualTo("대구");
        assertThat(maxCareer).isEqualTo(10);

    }

}
