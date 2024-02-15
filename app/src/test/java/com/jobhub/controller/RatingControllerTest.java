package com.jobhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobhub.controller.dto.RatingRequest;
import com.jobhub.controller.dto.RatingResponse;
import com.jobhub.controller.dto.RecruitmentRequest;
import com.jobhub.controller.dto.RecruitmentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class RatingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 별점주기() throws Exception {

        //given
        RecruitmentResponse recruitmentResponse = callCreateRecruitment();
        RatingRequest ratingRequest = new RatingRequest(
                recruitmentResponse.getId(),
                "user123",
                5,
                "회사 좋아요."
        );
        String jsonInput = objectMapper.writeValueAsString(ratingRequest);
        //when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/rating")
                        .content(jsonInput)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        String actualResponseBody = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        RatingResponse actualResponse = objectMapper.readValue(actualResponseBody, RatingResponse.class);

        //then
        assertThat(actualResponse.getUserId()).isEqualTo("user123");
        assertThat(Integer.toString(actualResponse.getStar())).isEqualTo("5");
        assertThat(actualResponse.getComment()).isEqualTo("회사 좋아요.");

    }

    private RecruitmentResponse callCreateRecruitment() throws Exception {
        RecruitmentRequest recruitmentRequest = new RecruitmentRequest (
                "https://www.jobkorea.co.kr/",
                "jobkorea",
                "프론트엔드 개발자",
                "엔지니어",
                "프론트엔드 개발자",
                "쿠팡",
                "서울시 강남구",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(30),
                "hash"
        );

        String jsonInput = objectMapper.writeValueAsString(recruitmentRequest);

        ResultActions resultActions = mockMvc.perform(post("/api/v1//recruitments")
                        .content(jsonInput)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
        String actualResponseBody = resultActions.andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(actualResponseBody, RecruitmentResponse.class);
    }
}
