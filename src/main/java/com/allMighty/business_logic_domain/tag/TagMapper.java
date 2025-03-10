package com.allMighty.business_logic_domain.tag;

import static com.example.jooq.generated.tables.Tag.TAG;

import com.allMighty.enitity.TagEntity;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Result;

import java.util.HashSet;
import java.util.Set;

public class TagMapper {

  public static final String TAGS_KEYWORD = "tags";

  public static TagDTO toTagDTO(TagEntity tag) {
    TagDTO tagDTO = new TagDTO();
    tagDTO.setId(tag.getId());
    tagDTO.setName(tag.getName());
    return tagDTO;
  }

  public static class TagJooqMapper implements RecordMapper<Record, TagEntity> {

    @Override
    public TagEntity map(Record record) {
      TagEntity tag = new TagEntity();

      tag.setId(record.get(TAG.ID));
      tag.setVersion(record.get(TAG.VERSION));
      tag.setName(record.get(TAG.NAME));

      return tag;
    }

    public static Set<TagEntity> mapTagEntities(Record record) {
      Result<Record> tagsResult = record.get(TAGS_KEYWORD, Result.class);
      Set<TagEntity> tags = new HashSet<>();
      if (tagsResult != null) {
        for (Record tagRecord : tagsResult) {
          TagEntity tag = new TagEntity();
          tag.setId(tagRecord.get(TAG.ID));
          tag.setName(tagRecord.get(TAG.NAME));
          tag.setVersion(tagRecord.get(TAG.VERSION));
          tags.add(tag);
        }
      }
      return tags;
    }
  }
}
