package com.allMighty.business_logic_domain.tag;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TagService {

  private final TagRepository tagRepository;

  public List<TagDTO> getAllTags() {
    return tagRepository.getAllTags()
            .stream()
            .map(TagMapper::toTagDTO)
            .toList();
  }
}
