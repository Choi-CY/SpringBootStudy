package com.example.springbootstudy.service;

import com.example.springbootstudy.domain.Article;
import com.example.springbootstudy.dto.AddArticleRequest;
import com.example.springbootstudy.dto.UpdateArticleRequest;
import com.example.springbootstudy.repository.BlogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll(){
        return blogRepository.findAll();
    }

    public Article findById(long id){
        return blogRepository.findById(id) // jpa로 id 값이 없으면 에러 메세지 던짐
                .orElseThrow(()->new IllegalArgumentException("not found : "+ id));
    }

    public void deleteById(long id){
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found : " + id));

        article.update(request.getTitle(), request.getContent());
        return article;
    }



}
