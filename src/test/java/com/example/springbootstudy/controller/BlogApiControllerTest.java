package com.example.springbootstudy.controller;

import com.example.springbootstudy.domain.Article;
import com.example.springbootstudy.dto.UpdateArticleRequest;
import com.example.springbootstudy.repository.BlogRepository;
import com.example.springbootstudy.service.BlogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.web.context.WebApplicationContext;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogService blogService;

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        blogRepository.deleteAll();
    }

    @DisplayName("findAllArticles : 블로그 목록을 전체 조회한다.")
    @Test
    public void findAllArticles() throws Exception {
        //given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());
        //when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));
    }

    @DisplayName("findArticles : 블로그 글 조회에 성공한다.")
    @Test
    public void findArticles() throws Exception {
        //given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article saveArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        //when
        final ResultActions resultActions = mockMvc.perform(get(url,saveArticle.getId()));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.title").value(title));
    }
    @DisplayName("delete : 블로그 글을 삭제한다.")
    @Transactional
    @Test
    public void deleteArticle() throws Exception {
        //given
        final String url = "/api/articles/delete/{id}";
        final String title = "title";
        final String content = "content";

        Article saveArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        System.out.println(saveArticle.getId());
        //when
        final ResultActions resultActions = mockMvc.perform(get(url,saveArticle.getId()));

        //then
        resultActions
                .andExpect(status().isOk());

        List<Article> articleList = blogRepository.findAll();
        System.out.println(articleList);
    }
    @DisplayName("update : 블로그 글을 수정한다.")
    @Transactional
    @Test
    public void updateArticle() throws Exception {
        //given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        final String newTitle = "titleNew";
        final String newContent = "contentNew";

        Article saveArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest(newTitle,newContent);

        //when
        final ResultActions resultActions = mockMvc.perform(put(url,saveArticle.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateArticleRequest)));

        //then
        resultActions.andExpect(status().isOk());

        Article article = blogRepository.findById(saveArticle.getId()).get();

        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);
    }

}