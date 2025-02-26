package com.allMighty.business_logic_domain.tag;

import static com.example.jooq.generated.tables.Tag.TAG;

import com.allMighty.enitity.TagEntity;
import org.jooq.Record;
import org.jooq.RecordMapper;

public class TagMapper {

  public static TagDTO toTagDTO(TagEntity tag) {
    TagDTO tagDTO = new TagDTO();
    tagDTO.setId(tag.getId());
    tagDTO.setName(tag.getName());
    return tagDTO;
  }

  static class TagJooqMapper implements RecordMapper<Record, TagEntity> {

    @Override
    public TagEntity map(Record record) {
      TagEntity tag = new TagEntity();

      tag.setId(record.get(TAG.ID));
      tag.setVersion(record.get(TAG.VERSION));
      tag.setName(record.get(TAG.NAME));

      return tag;
    }
  }
}
