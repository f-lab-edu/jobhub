package com.jobhub.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "location_category")
public class LocationCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private LocationCategory parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LocationCategory> subCategories = new ArrayList<>();

    @Builder
    public LocationCategory(String title, LocationCategory parent, List<LocationCategory> subCategories) {
        this.title = title;
        this.parent = parent;
        this.subCategories = subCategories;
    }

    public void addLocationSubcategory(LocationCategory subCategory) {
        subCategories.add(subCategory);
        subCategory.changeParent(this);
    }

    private void changeParent(LocationCategory parent) {
        this.parent = parent;
    }
}
