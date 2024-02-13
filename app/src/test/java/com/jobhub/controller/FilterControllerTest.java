
package com.jobhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobhub.controller.dto.FilterListResponse;
import com.jobhub.controller.dto.JobCategoryResponse;
import com.jobhub.controller.dto.JobSubCategoryResponse;
import com.jobhub.controller.dto.LocationCategoryResponse;
import com.jobhub.controller.dto.LocationSubCategoryResponse;
import com.jobhub.domain.JobCategory;
import com.jobhub.domain.LocationCategory;
import com.jobhub.repository.JobCategoryRepository;
import com.jobhub.repository.LocationCategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @Transactional
    @Test
    void 모든_필터_조회() throws Exception {

        //given
        JobCategory itCategory = new JobCategory("개발",null, new ArrayList<>());
        JobCategory devCategory = new JobCategory("웹 백엔드 엔지니어", itCategory, new ArrayList<>());
        itCategory.addSubCategory(devCategory);

        LocationCategory location = new LocationCategory("대구",null ,new ArrayList<>());
        LocationCategory locationCategoryEntity = new LocationCategory("달서구", location, new ArrayList<>());
        location.addLocationSubcategory(locationCategoryEntity);

        jobCategoryRepository.save(itCategory);
        jobCategoryRepository.save(devCategory);

        locationCategoryRepository.save(location);
        locationCategoryRepository.save(locationCategoryEntity);

        JobSubCategoryResponse jobSubCategoryResponse =
                new JobSubCategoryResponse("웹 백엔드 엔지니어", 1);
        JobCategoryResponse jobCategoryResponse = new JobCategoryResponse(
                "개발", List.of(jobSubCategoryResponse), 1);

        LocationSubCategoryResponse locationSubCategoryResponse =
                new LocationSubCategoryResponse(1L, "달서구");
        LocationCategoryResponse locationCategoryResponse =
                new LocationCategoryResponse(1L, "대구", List.of(locationSubCategoryResponse));

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/filters"))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        FilterListResponse actualResponse = objectMapper.readValue(contentAsString, FilterListResponse.class);

        JobCategoryResponse actualJobCategoryResponses = actualResponse.getCategories().get(0);
        LocationCategoryResponse actualLocationResponses = actualResponse.getLocations().get(0);
        int maxCareer = actualResponse.getMaxCareer();

        //then
        assertThat(actualJobCategoryResponses.getTitle()).isEqualTo(jobCategoryResponse.getTitle());
        assertThat(actualLocationResponses.getName()).isEqualTo("대구");
        assertThat(maxCareer).isEqualTo(20);

    }
}

