package com.example.springbootstudy.dto;

import com.example.springbootstudy.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddArticleRequest {
    private String title;
    private String content;

    public Article toEntity(){
        return Article.builder().
                title(title)
                .content(content)
                .build();
    }
}
