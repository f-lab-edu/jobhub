package com.jobhub.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocationCategoryTest {

    @DisplayName("지역 카테고리 생성과 필드 설정이 올바른지 테스트한다.")
    @Test
    void createLocationCategory() {
        // given
        String title = "서울";
        LocationCategory parent = LocationCategory.builder()
                .title("송파구")
                .build();

        // when
        LocationCategory locationCategory = LocationCategory.builder()
                .title(title)
                .parent(parent)
                .build();

        // then
        assertThat(locationCategory.getTitle()).isEqualTo(title);
        assertThat(locationCategory.getParent().getTitle()).isEqualTo("송파구");
    }

}
