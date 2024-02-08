package com.jobhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobhub.controller.dto.RatingRequest;
import com.jobhub.controller.dto.RatingResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

@SpringBootTest
@AutoConfigureMockMvc
class RatingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 별점주기_업데이트_확인() throws Exception {

        //given
        RatingRequest updateRequest = new RatingRequest(1L,"user123", 5, "회사 좋아요.");
        String jsonInput = objectMapper.writeValueAsString(updateRequest);

        MultiValueMap<String, String> expectResponseBody = new LinkedMultiValueMap<>();
        expectResponseBody.add("userId", "user123");
        expectResponseBody.add("star", Integer.toString(5));
        expectResponseBody.add("comment", "회사 좋아요.");

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/1/rating")
                        .content(jsonInput)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        String actualResponseBody = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        RatingResponse actualResponse = objectMapper.readValue(actualResponseBody, RatingResponse.class);

        //then
        assertThat(actualResponse.getUserId()).isEqualTo(expectResponseBody.getFirst("userId"));
        assertThat(Integer.toString(actualResponse.getStar())).isEqualTo(expectResponseBody.getFirst("star"));
        assertThat(actualResponse.getComment()).isEqualTo(expectResponseBody.getFirst("comment"));

    }

}
