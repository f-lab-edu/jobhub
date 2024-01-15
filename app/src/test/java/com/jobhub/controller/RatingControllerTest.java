package com.jobhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobhub.controller.dto.RatingRequest;
import com.jobhub.controller.dto.RatingResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.nio.charset.StandardCharsets;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RatingController.class)
class RatingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 별점주기_업데이트_확인() throws Exception {

        RatingRequest updateRequest = new RatingRequest("user123", 5, "회사 좋아요.");
        String jsonInput = objectMapper.writeValueAsString(updateRequest);

        MultiValueMap<String, String> expectResponseBody = new LinkedMultiValueMap<>();
        expectResponseBody.add("userId", "user123");
        expectResponseBody.add("star", Integer.toString(5));
        expectResponseBody.add("comment", "회사 좋아요.");

        ResultActions resultActions = mockMvc.perform(post("/api/v1/1/rating")
                        .content(jsonInput)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        String actualResponseBody = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        RatingResponse actualResponse = objectMapper.readValue(actualResponseBody, RatingResponse.class);

        assertThat(actualResponse.getUserId()).isEqualTo(expectResponseBody.getFirst("userId"));
        assertThat(Integer.toString(actualResponse.getStar())).isEqualTo(expectResponseBody.getFirst("star"));
        assertThat(actualResponse.getComment()).isEqualTo(expectResponseBody.getFirst("comment"));

    }

}
