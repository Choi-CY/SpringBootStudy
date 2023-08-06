package com.example.springbootstudy.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false)
    private Long id;

    @Column(name = "title",updatable = false)
    private String title;

    @Column(name = "content",updatable = false)
    private String content;


    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
    @Builder
    public Article(String title, String content){
        this.title = title;
        this.content = content;
    }


}
