package com.jobhub.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(FilterController.class)
class FilterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 필터리스트_존재하는지_확인() throws Exception {
        String categoriesByTitle = "$.categories[?(@.title == '%s')]";
        String locationByName = "$.locations[?(@.name == '%s')]";

        mockMvc.perform(get("/api/v1/filters"))
                .andExpect(MockMvcResultMatchers.jsonPath(categoriesByTitle,"엔지니어").exists())
                .andExpect(MockMvcResultMatchers.jsonPath(locationByName,"대구").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.locations[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.maxCareer").exists())
                .andDo(print());

    }

}
