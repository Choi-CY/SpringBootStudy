package com.example.springbootstudy.controller;

import com.example.springbootstudy.domain.Article;
import com.example.springbootstudy.dto.AddArticleRequest;
import com.example.springbootstudy.dto.ArticleResponse;
import com.example.springbootstudy.dto.UpdateArticleRequest;
import com.example.springbootstudy.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id){
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id,
                                                 @RequestBody UpdateArticleRequest request) {
        Article updateArticle = blogService.update(id, request);
        return ResponseEntity.ok()
                .body(updateArticle);
    }
    @GetMapping("/api/articles/delete/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id){
        blogService.deleteById(id);

        return ResponseEntity.ok()
                .build();
    }
}
