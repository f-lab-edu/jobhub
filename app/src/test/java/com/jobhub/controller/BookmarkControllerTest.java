package com.jobhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobhub.controller.dto.BookmarkRequest;
import com.jobhub.controller.dto.BookmarkResponse;
import com.jobhub.controller.dto.RecruitmentRequest;
import com.jobhub.controller.dto.RecruitmentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class BookmarkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 북마크_업데이트_확인() throws Exception {
        RecruitmentResponse recruitmentResponse = callCreateRecruitment();

        BookmarkRequest updateRequest = new BookmarkRequest("user123", recruitmentResponse.getId());
        String jsonInput = objectMapper.writeValueAsString(updateRequest);


        ResultActions resultActions = mockMvc.perform(patch("/api/v1/bookmark")
                        .content(jsonInput)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        String actualResponseBody = resultActions.andReturn().getResponse().getContentAsString();
        BookmarkResponse bookmarkResponse = objectMapper.readValue(actualResponseBody, BookmarkResponse.class);

        assertThat(bookmarkResponse.getUserId()).isEqualTo(updateRequest.getUserId());
        assertThat(bookmarkResponse.getRecruitmentId()).isEqualTo(updateRequest.getRecruitmentId());

    }

    @Test
    void 북마크_하기() throws Exception {

        RecruitmentResponse recruitmentResponse = callCreateRecruitment();

        BookmarkRequest bookmarkRequest = new BookmarkRequest("user123", recruitmentResponse.getId());

        ResultActions resultActions = mockMvc.perform(patch("/api/v1/bookmark")
                        .content(objectMapper.writeValueAsString(bookmarkRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        String actualResponseBody = resultActions.andReturn().getResponse().getContentAsString();
        BookmarkResponse bookmarkResponse = objectMapper.readValue(actualResponseBody, BookmarkResponse.class);

        assertThat(bookmarkResponse.getUserId()).isEqualTo(bookmarkRequest.getUserId());
        assertThat(bookmarkResponse.getRecruitmentId()).isEqualTo(bookmarkRequest.getRecruitmentId());
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
