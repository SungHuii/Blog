//package me.sunghui.springbootdeveloper.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import me.sunghui.springbootdeveloper.config.error.ErrorCode;
//import me.sunghui.springbootdeveloper.domain.Article;
//import me.sunghui.springbootdeveloper.domain.User;
//import me.sunghui.springbootdeveloper.dto.AddArticleRequest;
//import me.sunghui.springbootdeveloper.dto.UpdateArticleRequest;
//import me.sunghui.springbootdeveloper.repository.BlogRepository;
//import me.sunghui.springbootdeveloper.repository.UserRepository;
//import net.datafaker.Faker;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.security.Principal;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest // 테스트용 애플리케이션 컨텍스트
//@AutoConfigureMockMvc // MockMvc 생성 및 자동 구성
//class BlogApiControllerTest {
//
//    @Autowired
//    protected MockMvc mockMvc;
//
//    @Autowired
//    protected ObjectMapper objectMapper; // 직렬화, 역직렬화를 위한 클래스
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired
//    BlogRepository blogRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    User user;
//
//    @BeforeEach // 테스트 실행 전 실행하는 메서드
//    public void mockMvcSetUp(){
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
//                .build();
//        blogRepository.deleteAll();
//    }
//
//    @BeforeEach
//    void setSecurityContext() {
//        userRepository.deleteAll();
//        user = userRepository.save(User.builder()
//                .email("user@gmail.com")
//                .password("test")
//                .build());
//
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(new UsernamePasswordAuthenticationToken(user,
//                user.getPassword(), user.getAuthorities()));
//    }
//
//    @DisplayName("addArticle: 블로그 글 추가에 성공한다")
//    @Test
//    public void addArticle() throws Exception {
//        // given
//        final String url = "/api/articles";
//        final String title = "title";
//        final String content = "content";
//        final AddArticleRequest userRequest = new AddArticleRequest(title, content);
//
//        // 객체 JSON 으로 직렬화
//        final String requestBody = objectMapper.writeValueAsString(userRequest);
//
//        Principal principal = Mockito.mock(Principal.class);
//        Mockito.when(principal.getName()).thenReturn("username");
//
//        // when
//        // 설정한 내용을 바탕으로 전송
//        ResultActions result = mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .principal(principal)
//                .content(requestBody));
//
//        // then
//        result.andExpect(status().isCreated());
//
//        List<Article> articles = blogRepository.findAll();
//
//        assertThat(articles.size()).isEqualTo(1); // 크기가 1인지 검증
//        assertThat(articles.get(0).getTitle()).isEqualTo(title);
//        assertThat(articles.get(0).getContent()).isEqualTo(content);
//    }
//
//    // 글 목록 전체 조회 테스트
//    @DisplayName("findAllArticles: 블로그 글 목록 조회에 성공한다.")
//    @Test
//    public void findAllArticles() throws Exception {
//        // given
//        final String url = "/api/articles";
////        final String title = "title";
////        final String content = "content";
//
////        blogRepository.save(Article.builder()
////                .title(title)
////                .content(content)
////                .build());
//        Article savedArticle = createDefaultArticle();
//        // 제목, 내용 등을 createDefaultArticle() 메서드로 생성할 것이므로
//        // 기존에 있던 코드를 대치해줌
//
//        // when
//        final ResultActions resultActions = mockMvc.perform(get(url)
//                .accept(MediaType.APPLICATION_JSON));
//
//        // then
//        resultActions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].content").value(savedArticle.getContent()))
//                .andExpect(jsonPath("$[0].title").value(savedArticle.getTitle()));
//    }
//
//    // 글 목록 중 하나 조회 테스트
//    @DisplayName("findArticle: 블로그 글 조회에 성공한다")
//    @Test
//    public void findArticle() throws Exception{
//        // given
//        final String url = "/api/articles/{id}";
////        final String title = "title";
////        final String content = "content";
//
////        Article savedArticle = blogRepository.save(Article.builder()
////                .title(title)
////                .content(content)
////                .build());
//        Article savedArticle = createDefaultArticle();
//
//        // when
//        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));
//
//        // then
//        resultActions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").value(savedArticle.getContent()))
//                .andExpect(jsonPath("$.title").value(savedArticle.getTitle()));
//    }
//
//    // 블로그 글 삭제
//    @DisplayName("deleteArticle: 블로그 글 삭제에 성공한다")
//    @Test
//    public void deleteArticle() throws Exception{
//        // given
//        final String url = "/api/articles/{id}";
//        Article savedArticle = createDefaultArticle();
////        final String title = "title";
////        final String content = "content";
////
////        Article savedArticle = blogRepository.save(Article.builder()
////                .title(title)
////                .content(content).build());
//
//        // when
//        mockMvc.perform(delete(url, savedArticle.getId()))
//                .andExpect(status().isOk());
//
//        // then
//        List<Article> articles = blogRepository.findAll();
//
//        assertThat(articles).isEmpty();
//    }
//
//    // 블로그 글 수정
//    @DisplayName("updateArticle: 블로그 글 수정에 성공한다")
//    @Test
//    public void updateArticle() throws Exception {
//        // given
//        final String url = "/api/articles/{id}";
//        Article savedArticle = createDefaultArticle();
////        final String title = "title";
////        final String content = "content";
////
////        Article savedArticle = blogRepository.save(Article.builder()
////                .title(title)
////                .content(content)
////                .build());
//
//        final String newTitle = "new Title";
//        final String newContent = "new Content";
//
//        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);
//
//        // when
//        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(request)));
//
//        // then
//        result.andExpect(status().isOk());
//
//        Article article = blogRepository.findById(savedArticle.getId()).get();
//
//        assertThat(article.getTitle()).isEqualTo(newTitle);
//        assertThat(article.getContent()).isEqualTo(newContent);
//    }
//
//    private Article createDefaultArticle() {
//        return blogRepository.save(Article.builder()
//                .title("title")
//                .author(user.getUsername())
//                .content("content")
//                .build());
//    }
//
//    @DisplayName("addArticle: 아티클 추가할 때 title이 null이면 실패한다.")
//    @Test
//    public void addArticleNullValidation() throws Exception {
//        // given
//        // 블로그 글 추가에 필요한 요청 객체를 만듬
//        // 이때 title에는 null 값으로 설정
//        final String url = "/api/articles";
//        final String title = null;
//        final String content = "content";
//        final AddArticleRequest userRequest = new AddArticleRequest(title, content);
//
//        final String requestBody = objectMapper.writeValueAsString(userRequest);
//
//        Principal principal = Mockito.mock(Principal.class);
//        Mockito.when(principal.getName()).thenReturn("username");
//
//        // when
//        // 블로그 글 추가 API에 요청을 보냄
//        // 이 때 요청타입은 JSON, given절에서 미리 만들어둔 객체를 요청 본문으로 함께 보냄
//        ResultActions result = mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .principal(principal)
//                .content(requestBody));
//
//        // then
//        // 응답 코드가 400 Bad Request인지 확인
//        result.andExpect(status().isBadRequest());
//    }
//
//    @DisplayName("addArticle: 아티클 추가할 때 title이 10자를 넘으면 실패한다.")
//    @Test
//    public void addArticleSizeValidation() throws Exception {
//        // given
//        // 블로그 글 추가에 필요한 요청 객체를 만듦
//        // 이때 title에는 11자의 문자가 들어가게 설정
//        Faker faker = new Faker();
//
//        final String url = "/api/articles";
//        final String title = faker.lorem().characters(11);
//        final String content = "content";
//        final AddArticleRequest userRequest = new AddArticleRequest(title, content);
//
//        final String requestBody = objectMapper.writeValueAsString(userRequest);
//
//        Principal principal = Mockito.mock(Principal.class);
//        Mockito.when(principal.getName()).thenReturn("username");
//
//        // when
//        // 블로그 글 추가 API에 요청을 보냄
//        // 이때 요청 타입은 JSON이며, given절에서 미리 만들어둔 객체를 요청 본문으로 함께 보냄
//        ResultActions result = mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .principal(principal)
//                .content(requestBody));
//
//        // then
//        // 응답 코드가 400 Bad Request인지 확인
//        result.andExpect(status().isBadRequest());
//    }
//
//    @DisplayName("findArticle: 잘못된 HTTP 메서드로 아티클을 조회하려고 하면 조회에 실패한다.")
//    @Test
//    public void invalidHttpMethod() throws Exception {
//        // given
//        final String url = "/api/articles/{id}";
//
//        // when
//        final ResultActions resultActions = mockMvc.perform(post(url,1));
//
//        // then
//        resultActions
//                .andDo(print())
//                // Do(print())를 쓰면 실제 응답이 어떻게 나오는지 콘솔 로그에서 확인 가능
//                .andExpect(status().isMethodNotAllowed())
//                .andExpect(jsonPath("$.message").value(ErrorCode.METHOD_NOT_ALLOWED.getMessage()));
//    }
//
//    @DisplayName("findArticle: 존재하지 않는 아티클을 조회하려고 하면 조회에 실패한다.")
//    @Test
//    public void findArticleInvalidArticle() throws Exception {
//        // given
//        final String url = "/api/articles/{id}";
//        final long invalidId = 1;
//
//        // when
//        final ResultActions resultActions = mockMvc.perform(get(url, invalidId));
//
//        System.out.println(resultActions);
//        // then
//        resultActions
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value(ErrorCode.ARTICLE_NOT_FOUND.getMessage()))
//                .andExpect(jsonPath("$.code").value(ErrorCode.ARTICLE_NOT_FOUND.getCode()));
//
//    }
//}


package me.sunghui.springbootdeveloper.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.sunghui.springbootdeveloper.config.error.ErrorCode;
import me.sunghui.springbootdeveloper.domain.Article;
import me.sunghui.springbootdeveloper.domain.Comment;
import me.sunghui.springbootdeveloper.domain.User;
import me.sunghui.springbootdeveloper.dto.AddArticleRequest;
import me.sunghui.springbootdeveloper.dto.AddCommentRequest;
import me.sunghui.springbootdeveloper.dto.UpdateArticleRequest;
import me.sunghui.springbootdeveloper.repository.BlogRepository;
import me.sunghui.springbootdeveloper.repository.CommentRepository;
import me.sunghui.springbootdeveloper.repository.UserRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
    BlogRepository blogRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    User user;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        blogRepository.deleteAll();
        commentRepository.deleteAll();
    }


    @BeforeEach
    void setSecurityContext() {
        userRepository.deleteAll();
        user = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }

    @DisplayName("addComment: 댓글 추가에 성공한다.")
    @Test
    public void addComment() throws Exception {
        // given
        final String url = "/api/comments";

        Article savedArticle = createDefaultArticle();
        final Long articleId = savedArticle.getId();
        final String content = "content";
        final AddCommentRequest userRequest = new AddCommentRequest(articleId, content);
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .principal(principal)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Comment> comments = commentRepository.findAll();

        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getArticle().getId()).isEqualTo(articleId);
        assertThat(comments.get(0).getContent()).isEqualTo(content);
    }


    @DisplayName("addArticle: 아티클 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);

        final String requestBody = objectMapper.writeValueAsString(userRequest);

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .principal(principal)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("addArticle: 아티클 추가할 때 title이 null이면 실패한다.")
    @Test
    public void addArticleNullValidation() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = null;
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);

        final String requestBody = objectMapper.writeValueAsString(userRequest);

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .principal(principal)
                .content(requestBody));

        // then
        result.andExpect(status().isBadRequest());
    }

    @DisplayName("addArticle: 아티클 추가할 때 title이 10자를 넘으면 실패한다.")
    @Test
    public void addArticleSizeValidation() throws Exception {
        // given
        Faker faker = new Faker();

        final String url = "/api/articles";
        final String title = faker.lorem().characters(11);
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);

        final String requestBody = objectMapper.writeValueAsString(userRequest);

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");

        System.out.println("url: " + url);
        System.out.println("title: " + title);
        System.out.println("content: " + content);
        System.out.println("userRequest: " + userRequest);
        System.out.println("requestBody: " + requestBody);
        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .principal(principal)
                .content(requestBody));

        System.out.println(result);
        // then
        result.andExpect(status().isBadRequest());
    }

    @DisplayName("findAllArticles: 아티클 목록 조회에 성공한다.")
    @Test
    public void findAllArticles() throws Exception {
        // given
        final String url = "/api/articles";
        Article savedArticle = createDefaultArticle();

        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(savedArticle.getContent()))
                .andExpect(jsonPath("$[0].title").value(savedArticle.getTitle()));
    }

    @DisplayName("findArticle: 아티클 단건 조회에 성공한다.")
    @Test
    public void findArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(savedArticle.getContent()))
                .andExpect(jsonPath("$.title").value(savedArticle.getTitle()));
    }


    @DisplayName("deleteArticle: 아티클 삭제에 성공한다.")
    @Test
    public void deleteArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();

        // when
        mockMvc.perform(delete(url, savedArticle.getId()))
                .andExpect(status().isOk());

        // then
        List<Article> articles = blogRepository.findAll();

        assertThat(articles).isEmpty();
    }


    @DisplayName("updateArticle: 아티클 수정에 성공한다.")
    @Test
    public void updateArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();

        final String newTitle = "new title";
        final String newContent = "new content";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

        // when
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        Article article = blogRepository.findById(savedArticle.getId()).get();

        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);
    }

    private Article createDefaultArticle() {
        return blogRepository.save(Article.builder()
                .title("title")
                .author(user.getUsername())
                .content("content")
                .build());
    }

    @DisplayName("findArticle: 잘못된 HTTP 메서드로 아티클을 조회하려고 하면 조회에 실패한다.")
    @Test
    public void invalidHttpMethod() throws Exception {
        // given
        final String url = "/api/articles/{id}";

        // when
        final ResultActions resultActions = mockMvc.perform(post(url,1));

        // then
        resultActions
                .andDo(print())
                // Do(print())를 쓰면 실제 응답이 어떻게 나오는지 콘솔 로그에서 확인 가능
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.message").value(ErrorCode.METHOD_NOT_ALLOWED.getMessage()));
    }

    @DisplayName("findArticle: 존재하지 않는 아티클을 조회하려고 하면 조회에 실패한다.")
    @Test
    public void findArticleInvalidArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        final long invalidId = 1;

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, invalidId));

        System.out.println(resultActions);
        // then
        resultActions
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(ErrorCode.ARTICLE_NOT_FOUND.getMessage()))
                .andExpect(jsonPath("$.code").value(ErrorCode.ARTICLE_NOT_FOUND.getCode()));

    }
}