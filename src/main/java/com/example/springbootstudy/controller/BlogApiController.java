package com.example.springbootstudy.controller;

import com.example.springbootstudy.domain.Article;
import com.example.springbootstudy.dto.AddArticleRequest;
import com.example.springbootstudy.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BlogApiController {
    private final BlogService blogService;

    @PatchMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request){
        Article saveArticle = blogService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saveArticle);
    }
}
