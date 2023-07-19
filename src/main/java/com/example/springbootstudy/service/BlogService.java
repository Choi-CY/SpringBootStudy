package com.example.springbootstudy.service;

import com.example.springbootstudy.domain.Article;
import com.example.springbootstudy.dto.AddArticleRequest;
import com.example.springbootstudy.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request){
        return blogRepository.save(request.toEntity());
    }
}
