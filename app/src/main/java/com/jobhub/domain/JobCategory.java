package com.jobhub.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "job_category")
public class JobCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private JobCategory parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobCategory> subCategories = new ArrayList<>();

    @Builder
    public JobCategory(String title, JobCategory parent, List<JobCategory> subCategories) {
        this.title = title;
        this.parent = parent;
        this.subCategories = subCategories;
    }
    public void addSubCategory(JobCategory subCategory) {
        subCategories.add(subCategory);
        subCategory.changeParent(this);
    }

    private void changeParent(JobCategory parent) {
        this.parent = parent;
    }

}
