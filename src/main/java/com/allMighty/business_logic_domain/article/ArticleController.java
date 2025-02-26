package com.allMighty.business_logic_domain.article;

import com.allMighty.client.UrlProperty.Article;
import com.allMighty.global_operation.response.page.EntityPageResponseDTO;
import com.allMighty.global_operation.response.page.PageDescriptor;
import com.allMighty.global_operation.response.EntityResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.allMighty.client.UrlProperty.ID_PATH;
import static com.allMighty.global_operation.response.ResponseFactory.createPage;
import static com.allMighty.global_operation.response.ResponseFactory.createResponse;

@RestController
@RequestMapping(Article.PATH)
@RequiredArgsConstructor
@Validated
public class ArticleController {
  private final ArticleService articleService;

  @GetMapping
  public ResponseEntity<EntityPageResponseDTO<ArticleDTO>> getAllArticles(
      @RequestParam(name = "page", defaultValue = "1") Long page,
      @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize,
      @RequestParam(value = "filter", defaultValue = "") List<String> filters) {

    Long count = articleService.count(filters);
    PageDescriptor pageDescriptor = new PageDescriptor(page, pageSize);
    List<ArticleDTO> articleDTOs = articleService.getArticles(filters, pageDescriptor);
    return ResponseEntity.ok(createPage(count, articleDTOs, pageDescriptor));
  }

  @GetMapping(ID_PATH)
  public ResponseEntity<EntityResponseDTO<ArticleDTO>> getArticleById(
      @PathVariable(name = "id") Long id) {

    ArticleDTO articleDTO = articleService.getArticleById(id);
    return ResponseEntity.ok(createResponse(articleDTO));
  }

  @PostMapping
  public ResponseEntity<EntityResponseDTO<ArticleDTO>> createArticle(
      @RequestBody @Valid ArticleDTO articleDTO) {

    Long articleId = articleService.createArticle(articleDTO);
    ArticleDTO createdArticle = articleService.getArticleById(articleId);

    return ResponseEntity.ok(createResponse(createdArticle));
  }

  @PutMapping(ID_PATH)
  public ResponseEntity<EntityResponseDTO<ArticleDTO>> updateArticle(
      @PathVariable(name = "id") Long id, @RequestBody @Valid ArticleDTO articleDTO) {

    Long articleId = articleService.updateArticle(id, articleDTO);
    ArticleDTO updatedArticle = articleService.getArticleById(articleId);
    return ResponseEntity.ok(createResponse(updatedArticle));
  }
}
