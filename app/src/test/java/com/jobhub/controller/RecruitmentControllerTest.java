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
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        createRecruitment();
        //when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/recruitments")
                        .param("pageSize", "20")
                        .param("pageNo", "1")
                        .param("sortBy", "startDate")
                        .param("category", "ALL"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        TypeReference<PageResponse<RecruitmentResponse>> typeReference =
                new TypeReference<>() {
                };
        PageResponse<RecruitmentResponse> pageResponse = objectMapper.readValue(contentAsString, typeReference);
        RecruitmentResponse response1 = pageResponse.getContents().get(0);

        //the
        assertThat(response1.getCompanyName()).isEqualTo("라인");
        assertThat(response1.getCompanyAddress()).isEqualTo("도쿄");

    }

    @Test
    void 채용공고_등록() throws Exception {

        //given
        ResultActions resultActions = createRecruitment();
        //then
        MvcResult mvcResult = resultActions.andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        RecruitmentResponse actualResponse = objectMapper.readValue(actualResponseBody, RecruitmentResponse.class);

        assertThat(actualResponse.getCompanyName()).isEqualTo("라인");
        assertThat(actualResponse.getCompanyAddress()).isEqualTo("도쿄");
        assertThat(actualResponse.getTitle()).isEqualTo("라인 백엔드 엔지니어");

    }

    public ResultActions  createRecruitment() throws Exception {
        RecruitmentRequest recruitmentRequest
                = new RecruitmentRequest(1L
                ,"https://www.Line.com"
                ,"라인"
                ,"라인 백엔드 엔지니어"
                ,"백엔드"
                ,"신입"
                ,"라인"
                ,"도쿄"
                ,LocalDateTime.of(2021,8,1,0,0)
                ,LocalDateTime.of(2021,8,31,0,0));

        //when

        return mockMvc.perform(post("/api/v1/recruitments")
                        .content(objectMapper.writeValueAsString(recruitmentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }

}
