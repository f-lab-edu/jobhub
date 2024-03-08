package com.jobhub.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobhub.controller.dto.RecruitmentRequest;
import com.jobhub.controller.dto.RecruitmentResponse;
import com.jobhub.global.wrapper.PageResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class RecruitmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void 저장된_채용공고_조회() throws Exception {

        //given
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = LocalDateTime.now().plusDays(30);
        RecruitmentRequest recruitmentRequest = new RecruitmentRequest(
                "https://www.Line.com",
                "라인",
                "라인 백엔드 엔지니어",
                "백엔드",
                "신입",
                "라인",
                "도쿄",
                startDateTime,
                endDateTime,
                "hash");

        mockMvc.perform(post("/api/v1/recruitment")
                        .content(objectMapper.writeValueAsString(recruitmentRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


        //when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/recruitments")
                        .param("pageSize", "20")
                        .param("pageNo", "1")
                        .param("sortBy", "startDate")
                        .param("category", "ALL"))
                .andDo(print())
                .andExpect(status().isOk());

        String actualResponseBody = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        RecruitmentResponse recruitmentResponse = objectMapper.readValue(actualResponseBody, RecruitmentResponse.class);

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        TypeReference<PageResponse<RecruitmentResponse>> typeReference =
                new TypeReference<>() {
                };
        PageResponse<RecruitmentResponse> pageResponse = objectMapper.readValue(contentAsString, typeReference);
        RecruitmentResponse response1 = pageResponse.getContents().get(0);

        //the
        assertThat(response1)
                .extracting("provider"
                        , "companyName"
                        , "companyAddress"
                        , "title"
                        , "startDate"
                        , "endDate")
                        .contains("라인", "라인", "도쿄", "라인 백엔드 엔지니어", startDateTime, endDateTime);
    }

    @Test
    void 채용공고_등록() throws Exception {

        //given
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = LocalDateTime.now().plusDays(30);

        RecruitmentRequest recruitmentRequest
                = new RecruitmentRequest(
                "https://www.Line.com"
                ,"라인"
                ,"라인 백엔드 엔지니어"
                ,"백엔드"
                ,"신입"
                ,"라인"
                ,"도쿄"
                ,startDateTime
                ,endDateTime
                ,"hash");

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/recruitment")
                        .content(objectMapper.writeValueAsString(recruitmentRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String actualResponseBody = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        RecruitmentResponse recruitmentResponse = objectMapper.readValue(actualResponseBody, RecruitmentResponse.class);

        //then;
        assertThat(recruitmentResponse.getCompanyName()).isEqualTo("라인");
        assertThat(recruitmentResponse.getCompanyAddress()).isEqualTo("도쿄");
        assertThat(recruitmentResponse.getTitle()).isEqualTo("라인 백엔드 엔지니어");

    }

    @Test
    void 채용공고_수정() throws Exception {

        //given
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = LocalDateTime.now().plusDays(30);
        RecruitmentRequest recruitmentRequest
                = new RecruitmentRequest(
                "https://www.Line.com"
                ,"라인"
                ,"라인 백엔드 엔지니어"
                ,"백엔드"
                ,"신입"
                ,"라인"
                ,"도쿄"
                ,startDateTime
                ,endDateTime
                ,"hash");

        mockMvc.perform(post("/api/v1/recruitment")
                        .content(objectMapper.writeValueAsString(recruitmentRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        RecruitmentRequest updateRequest = new RecruitmentRequest(
                "https://www.Line.com"
                ,"라인"
                ,"라인 백엔드 엔지니어 (서울)"
                ,"백엔드"
                ,"신입"
                ,"라인"
                ,"서울"
                ,startDateTime
                ,endDateTime
                ,"hash");

        //when
        ResultActions resultActions = mockMvc.perform(put("/api/v1/recruitments/update")
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String actualResponseBody = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        RecruitmentResponse actualResponse = objectMapper.readValue(actualResponseBody, RecruitmentResponse.class);

        //then
        assertThat(actualResponse.getTitle()).isEqualTo("라인 백엔드 엔지니어 (서울)");
        assertThat(actualResponse.getCompanyAddress()).isEqualTo("서울");
    }
}
