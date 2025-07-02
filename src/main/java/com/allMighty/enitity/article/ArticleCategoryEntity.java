package com.allMighty.enitity.article;

import com.allMighty.enitity.abstractEntity.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "article_category")
public class ArticleCategoryEntity  extends AbstractEntity {
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<ArticleEntity> articles = new HashSet<>();

}
