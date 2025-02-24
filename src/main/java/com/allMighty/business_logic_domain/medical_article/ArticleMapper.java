package com.allMighty.business_logic_domain.medical_article;

import com.allMighty.enitity.MedicalArticleEntity;
import org.jooq.Record;

import static com.example.jooq.generated.tables.MedicalArticle.MEDICAL_ARTICLE;

public class ArticleMapper {
    public static ArticleDTO toArticleDTO(Record record) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(record.get(MEDICAL_ARTICLE.ID));
        articleDTO.setTitle(record.get(MEDICAL_ARTICLE.TITLE));
        articleDTO.setContent(record.get(MEDICAL_ARTICLE.CONTENT));
        articleDTO.setAuthor(record.get(MEDICAL_ARTICLE.AUTHOR));
        articleDTO.setArchived(record.get(MEDICAL_ARTICLE.ARCHIVED));
        return articleDTO;
    }

    public static ArticleDTO toArticleDTO(MedicalArticleEntity medicalArticleEntity) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(medicalArticleEntity.getId());
        articleDTO.setTitle(medicalArticleEntity.getTitle());
        articleDTO.setContent(medicalArticleEntity.getContent());
        articleDTO.setAuthor(medicalArticleEntity.getAuthor());
        articleDTO.setArchived(medicalArticleEntity.isArchived());
        return articleDTO;
    }


    public static void mapDataToMedicalArticleEntity(ArticleDTO articleDTO, MedicalArticleEntity medicalArticleEntity) {
        medicalArticleEntity.setTitle(articleDTO.getTitle());
        medicalArticleEntity.setContent(articleDTO.getContent());
        medicalArticleEntity.setAuthor(articleDTO.getAuthor());
        medicalArticleEntity.setArchived(articleDTO.isArchived());
    }

}
