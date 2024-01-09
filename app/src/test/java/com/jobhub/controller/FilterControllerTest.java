package com.jobhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobhub.controller.dto.FilterListResponse;
import com.jobhub.controller.dto.JobCategoryResponse;
import com.jobhub.controller.dto.LocationCategoryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(FilterController.class)
class FilterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 각_필터목록_값들이_일치하는지_확인() throws Exception {

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

        assertThat(jobCategoryResponses.getTitle()).isEqualTo("엔지니어");
        assertThat(Location.getName()).isEqualTo("대구");
        assertThat(maxCareer).isEqualTo(10);

    }

}
