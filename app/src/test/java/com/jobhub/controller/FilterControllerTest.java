package com.jobhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobhub.controller.dto.FilterListResponse;
import com.jobhub.controller.dto.JobCategoryRequest;
import com.jobhub.controller.dto.JobCategoryResponse;
import com.jobhub.controller.dto.JobSubCategoryRequest;
import com.jobhub.controller.dto.LocationCategoryRequest;
import com.jobhub.controller.dto.LocationCategoryResponse;
import com.jobhub.controller.dto.LocationSubCategoryRequest;
import com.jobhub.domain.JobCategory;
import com.jobhub.domain.JobSubCategory;
import com.jobhub.domain.LocationCategory;
import com.jobhub.domain.LocationSubCategory;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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


    @Test
    void 각_필터목록_값들이_일치하는지_확인() throws Exception {
        //given
        JobSubCategoryRequest jobSubCategoryRequest = new JobSubCategoryRequest(1L, "웹 백엔드 엔지니어", 1);
        JobCategoryRequest jobCategoryRequest = new JobCategoryRequest(
                1L, "엔지니어", 10, List.of(jobSubCategoryRequest));

        LocationSubCategoryRequest locationSubCategory = new LocationSubCategoryRequest(1L, "달서구");
        LocationCategoryRequest locationCategoryRequest = new LocationCategoryRequest(1L, "대구", List.of(locationSubCategory));


        mockMvcPerformPostRequest("/api/v1/filters/job", jobCategoryRequest);

        mockMvcPerformPostRequest("/api/v1/filters/location", locationCategoryRequest);
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

    @Test
    void 직업_경력_필터_등록() throws Exception {
        //given
        JobSubCategory jobSubCategory = new JobSubCategory(1L, "웹 백엔드 엔지니어", 1);
        JobCategory jobCategoriesRequest =
                JobCategory.builder()
                        .id(1L)
                        .title("엔지니어")
                        .totalCount(10)
                        .subCategories(List.of(jobSubCategory))
                        .build();


        //when
        ResultActions resultActions = mockMvcPerformPostRequest("/api/v1/filters/job", jobCategoriesRequest);
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JobCategoryResponse response = objectMapper.readValue(contentAsString, JobCategoryResponse.class);

        assertThat(response.getTitle()).isEqualTo("엔지니어");
        assertThat(response.getSubCategories().get(0).getTitle()).isEqualTo("웹 백엔드 엔지니어");


    }

    @Test
    void 지역_필터_등록() throws Exception {
        //given
        LocationSubCategory locationSubCategory = new LocationSubCategory(1L, "달서구");
        LocationCategory locationCategoriesRequest = LocationCategory.builder()
                .id(1L)
                .name("대구")
                .subCategories(List.of(locationSubCategory))
                .build();

        //when
        ResultActions resultActions = mockMvcPerformPostRequest("/api/v1/filters/location", locationCategoriesRequest);
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        LocationCategoryResponse response = objectMapper.readValue(contentAsString, LocationCategoryResponse.class);

        //then
        assertThat(response.getName()).isEqualTo("대구");
        assertThat(response.getSubCategories().get(0).getName()).isEqualTo("달서구");

    }

    public ResultActions mockMvcPerformPostRequest(String url, Object content) throws Exception {
        return mockMvc.perform(post(url)
                        .content(objectMapper.writeValueAsString(content))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));


    }
}
