package com.igomall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "edu_material")
public class Material extends BaseEntity<Long> {

    @NotEmpty
    @Column(nullable = false,unique = true)
    private String url;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
