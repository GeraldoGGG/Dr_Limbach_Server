package com.allMighty.business_logic_domain.tag;

import com.allMighty.enitity.TagEntity;
import com.allMighty.global_operation.exception_management.exception.BadRequestException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.jooq.generated.tables.Tag.TAG;

@Repository
@RequiredArgsConstructor
public class TagRepository {
  private final DSLContext dslContext;

  private TagMapper.TagJooqMapper tagJooqMapper = new TagMapper.TagJooqMapper();

  public Set<TagEntity> handleTagEntities(Set<TagDTO> tags, EntityManager em) {
    if (CollectionUtils.isEmpty(tags)) {
      return Collections.emptySet();
    }
    List<TagEntity> allTags =
        getAllTags().stream().filter(t -> StringUtils.isNotEmpty(t.getName())).toList();

    Map<String, TagEntity> nameTagMap =
        allTags.stream().collect(Collectors.toMap(TagEntity::getName, Function.identity()));

    Map<Long, TagEntity> idTagMap =
        allTags.stream().collect(Collectors.toMap(TagEntity::getId, Function.identity()));

    Set<TagEntity> tagEntities = new HashSet<>();
    for (TagDTO tagDTO : tags) {
      Long tagId = tagDTO.getId();

      if (idTagMap.containsKey(tagId)) {
        TagEntity tagEntity = idTagMap.get(tagId);
        tagEntities.add(tagEntity);
        continue;
      }

      String name = tagDTO.getName();
      if (StringUtils.isEmpty(name)) {
        throw new BadRequestException("Tag name can not be empty!");
      }

      if (nameTagMap.containsKey(name)) {
        TagEntity tagEntity = nameTagMap.get(name);
        tagEntities.add(tagEntity);
        continue;
      }

      // create new
      TagEntity tagEntity = new TagEntity();
      tagEntity.setName(name);
      em.persist(tagEntity);
      tagEntities.add(tagEntity);
    }

    return tagEntities;
  }

  public List<TagEntity> getAllTags() {
    return dslContext.select(TAG.ID, TAG.NAME, TAG.VERSION).from(TAG).fetch(tagJooqMapper);
  }

/*  private static TagEntity getTagEntity(Record record) {
    TagEntity tagEntity = new TagEntity();
    tagEntity.setId(record.get(TAG.ID));
    tagEntity.setName(record.get(TAG.NAME));
    tagEntity.setVersion(record.get(TAG.VERSION));
    return tagEntity;
  }*/
}
