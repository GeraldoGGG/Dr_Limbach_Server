package com.allMighty.enitity;

import com.allMighty.enitity.abstractEntity.AbstractEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "article")
public class ArticleEntity extends AbstractEntity {

  private String title;
  private String author;

  @Column(columnDefinition = "TEXT")
  private String content;

  private boolean archived;
  private boolean removed;

  private LocalDateTime creationDate;

  private String summary;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  @JoinTable(
      name = "tag_article",
      joinColumns = @JoinColumn(name = "article_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<TagEntity> tags;
}
