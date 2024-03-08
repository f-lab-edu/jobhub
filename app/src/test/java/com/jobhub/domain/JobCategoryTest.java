package com.jobhub.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JobCategoryTest {

    @DisplayName("직업 카테고리 생성과 필드 설정이 올바른지 테스트한다.")
    @Test
    void createJobCategory() {
        // given
        String title = "엔지니어";
        JobCategory parent = JobCategory.builder()
                .title("백엔드 개발자")
                .build();

        // when
        JobCategory jobCategory = JobCategory.builder()
                .title(title)
                .parent(parent)
                .build();

        // then
        assertThat(jobCategory.getTitle()).isEqualTo(title);
        assertThat(jobCategory.getParent().getTitle()).isEqualTo("백엔드 개발자");
    }

}
