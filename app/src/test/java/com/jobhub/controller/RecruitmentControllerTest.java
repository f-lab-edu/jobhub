package com.jobhub.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobhub.controller.dto.RecruitmentResponse;
import com.jobhub.global.wrapper.PageResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecruitmentController.class)
class RecruitmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void 채용공고리스트_일치하는지_확인() throws Exception {

        MultiValueMap<String, String> pathParams = new LinkedMultiValueMap<>();
        pathParams.add("category", "Engineer");
        pathParams.add("pageSize", "1");
        pathParams.add("pageNo", "20");

        ResultActions resultActions = mockMvc.perform(get("/api/v1/recruitments")
                        .params(pathParams))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        TypeReference<PageResponse<RecruitmentResponse>> typeReference = new TypeReference<PageResponse<RecruitmentResponse>>() {
        };
        PageResponse<RecruitmentResponse> pageResponse = objectMapper.readValue(contentAsString, typeReference);
        RecruitmentResponse response1 = pageResponse.getContents().get(0);
        RecruitmentResponse response2 = pageResponse.getContents().get(1);

        assertThat(response1.getTitle()).isEqualTo("당근 백엔드 엔지니어");
        assertThat(response1.getCompanyName()).isEqualTo("당근");
        assertThat(response2.getCompanyName()).isEqualTo("라인");
        assertThat(response2.getCompanyAddress()).isEqualTo("도쿄");


    }

}
