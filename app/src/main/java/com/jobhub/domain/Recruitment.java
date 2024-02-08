package com.jobhub.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.Objects;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "recruitment",
        uniqueConstraints={
                @UniqueConstraint(columnNames = {"signed_hash"}, name = "UK_RECRUITMENT_SIGNED_HASH"),
        }
)
public class Recruitment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Comment("공고 URL")
    private String url;

    @Column(nullable = false)
    @Comment("공고 제공처")
    private String provider;

    @Column(nullable = false)
    @Comment("공고 제목")
    private String title;

    private String department;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "signed_hash", nullable = false)
    @Comment("해시")
    private String signedHash;

    @Builder
    public Recruitment(String url,
                       String provider,
                       String title,
                       String department,
                       String companyName,
                       String companyAddress,
                       LocalDateTime startDate,
                       LocalDateTime endDate,
                       String signedHash) {
        Objects.requireNonNull(url);
        Objects.requireNonNull(title);
        Objects.requireNonNull(provider);
        Objects.requireNonNull(companyName);
        Objects.requireNonNull(signedHash);
        this.url = url;
        this.provider = provider;
        this.title = title;
        this.department = department;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.startDate = startDate;
        this.endDate = endDate;
        this.signedHash = signedHash;
    }

    public void change(Recruitment newRecruitment) {
        this.title = newRecruitment.title;
        this.startDate = newRecruitment.startDate;
        this.endDate = newRecruitment.endDate;
    }
}
