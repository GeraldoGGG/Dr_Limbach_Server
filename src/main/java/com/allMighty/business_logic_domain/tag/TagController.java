package com.allMighty.business_logic_domain.tag;

import static com.allMighty.global_operation.response.ResponseFactory.createResponse;

import com.allMighty.client.UrlProperty;
import com.allMighty.global_operation.response.EntityResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlProperty.Tag.PATH)
@RequiredArgsConstructor
public class TagController {
  private final TagService tagService;

  @GetMapping
  public ResponseEntity<EntityResponseDTO<List<TagDTO>>> getAllTags() {
    return ResponseEntity.ok(createResponse(tagService.getAllTags()));
  }
}
