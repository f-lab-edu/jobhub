package com.jobhub.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobhub.controller.dto.RecruitmentResponse;
import com.jobhub.domain.Recruitment;
import com.jobhub.global.wrapper.PageResponse;
import com.jobhub.repository.RecruitmentRepository;
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

    @Autowired
    RecruitmentRepository recruitmentRepository;

    @Test
    void 저장된_채용공고_조회() throws Exception {

        //given
        Recruitment recruitment = Recruitment.builder()
                .id(1L)
                .url("https://www.Line.com")
                .title("라인 백엔드 엔지니어")
                .companyName("라인")
                .companyAddress("도쿄")
                .startDate(LocalDateTime.of(2021, 8, 1, 0, 0))
                .endDate(LocalDateTime.of(2021, 8, 31, 0, 0))
                .build();


        Recruitment recruitment2 = Recruitment.builder()
                .id(2L)
                .url("https://www.carrot.com")
                .title("당근 백엔드 엔지니어")
                .companyName("당근")
                .companyAddress("서울")
                .startDate(LocalDateTime.of(2021, 8, 1, 0, 0))
                .endDate(LocalDateTime.of(2021, 8, 31, 0, 0))
                .build();

        recruitmentRepository.save(recruitment);
        recruitmentRepository.save(recruitment2);
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
        RecruitmentResponse response2 = pageResponse.getContents().get(1);

        //then
        assertThat(response1.getCompanyName()).isEqualTo("라인");
        assertThat(response1.getCompanyAddress()).isEqualTo("도쿄");
        assertThat(response2.getTitle()).isEqualTo("당근 백엔드 엔지니어");
        assertThat(response2.getCompanyName()).isEqualTo("당근");


    }

}
