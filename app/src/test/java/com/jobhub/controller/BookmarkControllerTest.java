package com.jobhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobhub.controller.dto.BookmarkRequest;
import com.jobhub.controller.dto.BookmarkResponse;
import com.jobhub.domain.Recruitment;
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

        Recruitment recruitment = Recruitment.builder()
                .id(3L)
                .url("url")
                .provider("jobKorea")
                .title("쿠팡 프론트엔드 개발자")
                .companyName("쿠팡")
                .companyAddress("서울")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(10))
                .build();

        BookmarkRequest updateRequest = new BookmarkRequest("user123", true,recruitment);
        String jsonInput = objectMapper.writeValueAsString(updateRequest);

        String expectResponseUserId = "user123";
        Boolean expectResponseIsBookMark = true;

        ResultActions resultActions = mockMvc.perform(patch("/api/v1/bookmark/2")
                        .content(jsonInput)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        String actualResponseBody = resultActions.andReturn().getResponse().getContentAsString();
        BookmarkResponse bookmarkResponse = objectMapper.readValue(actualResponseBody, BookmarkResponse.class);

        assertThat(bookmarkResponse.getUserId()).isEqualTo(expectResponseUserId);
        assertThat(bookmarkResponse.getIsBookmark()).isEqualTo(expectResponseIsBookMark);

    }

}
