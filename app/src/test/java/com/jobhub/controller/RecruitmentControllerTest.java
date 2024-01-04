package com.jobhub.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecruitmentController.class)
class RecruitmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 채용공고리스트_일치하는지_확인() throws Exception {

        MultiValueMap<String, String> pathParams = new LinkedMultiValueMap<>();
        pathParams.add("category","Engineer");
        pathParams.add("pageSize","1");
        pathParams.add("pageNo","20");
        mockMvc.perform(get("/api/v1/recruitments")
                        .params(pathParams))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.contents[0].companyName").value("당근"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contents[0].companyAddress").value("서울"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contents[1].companyName").value("라인"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contents[1].companyAddress").value("도쿄"))
                .andDo(print());

    }

}
