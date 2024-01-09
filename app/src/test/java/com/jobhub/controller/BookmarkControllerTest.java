package com.jobhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobhub.controller.dto.BookmarkRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookmarkController.class)
class BookmarkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 북마크_업데이트_확인() throws Exception {

        BookmarkRequest updateRequest = new BookmarkRequest("user123", true);
        String jsonInput = objectMapper.writeValueAsString(updateRequest);

        MultiValueMap<String, String> input = new LinkedMultiValueMap<>();
        input.add("userId", "user123");
        input.add("isBookmark", "true");

        ResultActions resultActions = mockMvc.perform(patch("/api/v1/bookmark/2")
                        .content(jsonInput)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
