package com.allMighty.enitity;

import com.allMighty.enitity.abstractEntity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "event")
public class EventEntity extends AbstractEntity {

  private String title;

  @Column(columnDefinition = "TEXT")
  private String content;

  private LocalDateTime eventDate;

  @Column(name = "event_duration")
  private Long eventDuration;

  private Integer price;

  private boolean archived;
  private boolean removed;
}
