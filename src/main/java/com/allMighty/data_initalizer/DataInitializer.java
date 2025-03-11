package com.allMighty.data_initalizer;

import com.allMighty.business_logic_domain.analysis.AnalysisService;
import com.allMighty.business_logic_domain.analysis.dto.AnalysisDTO;
import com.allMighty.business_logic_domain.analysis.dto.AnalysisDetailDTO;
import com.allMighty.business_logic_domain.article.ArticleDTO;
import com.allMighty.business_logic_domain.article.ArticleService;
import com.allMighty.business_logic_domain.event.EventDTO;
import com.allMighty.business_logic_domain.event.EventService;
import com.allMighty.business_logic_domain.image.ImageDTO;
import com.allMighty.business_logic_domain.medical_service.MedicalServiceDTO;
import com.allMighty.business_logic_domain.medical_service.MedicalServiceService;
import com.allMighty.business_logic_domain.tag.TagDTO;
import com.allMighty.config.security.person.repository.PersonEntityRepository;
import com.allMighty.config.security.person.role.Role;
import com.allMighty.enitity.PersonEntity;
import com.allMighty.enumeration.ImageContentType;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
  private final ArticleService articleService;
  private final EventService eventService;
  private final AnalysisService analysisService;
  private final MedicalServiceService medicalServiceService;

  private final PersonEntityRepository personEntityRepository;

  @Override
  public void run(String... args) {
    log.error("Initializing data...");
    insertDummyMainUser();

    insertDummyArticles();
    insertDummyEvents();
    insertDummyAnalysis();
    insertDummyMedicalServices();
  }

  private void insertDummyMainUser() {
    if (personEntityRepository.count() == 0) {
      PersonEntity adminUser = new PersonEntity();
      adminUser.setFirstName("John");
      adminUser.setLastName("Doe");
      adminUser.setEmail("john.doe@example.com");
      adminUser.setPassword("$2b$12$Ol5VnvETmsFKwS.M5j0n0.Zj5KgWceosZbQ3rGEGWDwndLj.awTaS");
      adminUser.setRole(Role.ADMIN);
      personEntityRepository.save(adminUser);
    }
  }

  private void insertDummyMedicalServices() {
    if (medicalServiceService.count() != 0) {
      return;
    }

    for (int i = 1; i <= 15; i++) {
      MedicalServiceDTO medicalServiceDTO = new MedicalServiceDTO();
      medicalServiceDTO.setTitle("Medical Service " + i);
      medicalServiceDTO.setShowInHomePage(i % 2 == 0); // Show on homepage for even entries
      medicalServiceDTO.setArchived(false);
      medicalServiceDTO.setRemoved(false);
      medicalServiceDTO.setContent(getMedicalContent());

      List<Long> analysisIds = new ArrayList<>();
      for (int j = 1; j <= 5; j++) {
        analysisIds.add((long) j); // Dummy IDs
      }
      medicalServiceDTO.setAnalysisIds(analysisIds);

      List<Long> articleIds = new ArrayList<>();
      for (int j = 1; j <= 5; j++) {
        articleIds.add((long) j); // Dummy IDs
      }
      medicalServiceDTO.setArticleIds(articleIds);

      medicalServiceDTO.setTags(getTagContent(i));
      medicalServiceDTO.setImages(getImageContent());

      medicalServiceService.createMedicalService(medicalServiceDTO);
    }

    log.error("Generated 15 Medical Services!");
  }

  private void insertDummyAnalysis() {
    if (analysisService.count() != 0) {
      return;
    }

    for (int i = 1; i <= 15; i++) {
      // Create the AnalysisDTO
      AnalysisDTO analysisDTO = new AnalysisDTO();
      analysisDTO.setMedicalName("Medical Analysis " + i);
      analysisDTO.setSynonym("Synonym " + i);
      analysisDTO.setPrice(100 + i);
      analysisDTO.setArchived(false);
      analysisDTO.setRemoved(false);

      Set<TagDTO> tags = getTagContent(i);
      analysisDTO.setTags(tags);

      List<ImageDTO> images = getImageContent();
      analysisDTO.setImages(images);

      List<AnalysisDetailDTO> analysisDetails = new ArrayList<>();
      for (int j = 1; j <= 5; j++) {
        AnalysisDetailDTO detail = new AnalysisDetailDTO();
        detail.setKeyValue("key for Analysis " + i + " - " + j);
        detail.setStringValue("value for Analysis " + i + " - " + j);

        analysisDetails.add(detail);
      }
      analysisDTO.setDetails(analysisDetails);

      analysisService.createAnalysis(analysisDTO);
    }

    log.error("Generated 15 Analysis records with details, tags, and images!");
  }

  private void insertDummyArticles() {
    if (articleService.count() != 0) {
      return;
    }

    for (int i = 1; i <= 15; i++) {
      ArticleDTO articleDTO = new ArticleDTO();
      articleDTO.setTitle("Artikulli " + i);
      articleDTO.setAuthor("Autor " + i);
      articleDTO.setContent(getMedicalContent());
      articleDTO.setArchived(false);
      articleDTO.setTags(getTagContent(i));
      articleDTO.setImages(getImageContent());
      articleService.createArticle(articleDTO);
    }

    log.error("Generated 15 Articles !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
  }

  private void insertDummyEvents() {
    if (eventService.count() != 0) {
      return;
    }

    for (int i = 1; i <= 15; i++) {
      EventDTO eventDTO = new EventDTO();
      eventDTO.setTitle("Event " + i);
      eventDTO.setPrice(100);
      eventDTO.setContent(getMedicalContent());
      eventDTO.setArchived(false);
      eventDTO.setEventDate(LocalDateTime.of(2025, 3, 10, 10, 0));
      eventDTO.setEventDuration(10800L);
      eventDTO.setImages(getImageContent());
      eventService.createEvent(eventDTO);
    }

    log.error("Generated 15 Events !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
  }

  private List<ImageDTO> getImageContent() {
    ImageDTO imageDTO = new ImageDTO();
    imageDTO.setImageContentType(ImageContentType.JPEG);
    imageDTO.setImageData(getImageByteContent());
    return Collections.singletonList(imageDTO);
  }

  private String getImageByteContent() {
    try {
      ClassPathResource resource = new ClassPathResource("images/pregnant-woman.jpg");
      try (InputStream inputStream = resource.getInputStream()) {
        byte[] imageBytes = inputStream.readAllBytes();
        return Base64.getEncoder().encodeToString(imageBytes);
      }
    } catch (IOException e) {
      return null;
    }
  }

  private static String getMedicalContent() {
    // Rich HTML content in Albanian
    String medicalContent =
        "<h1>Diagnostikimi dhe Trajtimi i Sëmundjeve Kronike</h1>"
            + "<p>Në botën moderne, sëmundjet kronike kanë një ndikim të madh në cilësinë e jetës së individëve. "
            + "Ky artikull ofron një analizë të detajuar të metodave diagnostikuese dhe strategjive të trajtimit të këtyre sëmundjeve.</p>"
            + "<h2>Diagnostikimi</h2>"
            + "<p>Për një diagnozë të saktë, është e domosdoshme të kryhen testet e mëposhtme:</p>"
            + "<ul>"
            + "<li><strong>Analizat e gjakut:</strong> Për të identifikuar shenjat e inflamacionit dhe parimet biokimike.</li>"
            + "<li><strong>Imagjinimi mjekësor:</strong> Si MRI, CT, dhe ultratingulli për të vlerësuar strukturat e brendshme të trupit.</li>"
            + "<li><strong>Testet gjenetike:</strong> Për të zbuluar predispozicionin ndaj sëmundjeve të caktuara.</li>"
            + "</ul>"
            + "<h2>Trajtimi</h2>"
            + "<p>Strategjitë terapeutike përfshijnë:</p>"
            + "<ol>"
            + "<li><strong>Medikamentet:</strong> Për të kontrolluar simptomat dhe për të ngadalësuar progresin e sëmundjes.</li>"
            + "<li><strong>Intervencionet kirurgjikale:</strong> Kur është e nevojshme për të parandaluar komplikacionet.</li>"
            + "<li><strong>Ndryshimet e stilit të jetës:</strong> Përfshirë dieta të shëndetshme, ushtrime fizike, dhe menaxhimin e stresit.</li>"
            + "</ol>"
            + "<p>Ky qasje gjithëpërfshirëse ndihmon në përmirësimin e cilësisë së jetës së pacientëve dhe zvogëlon kostot afatgjata të kujdesit shëndetësor.</p>"
            + "<p><em>Shënim:</em> Konsultohuni me një specialist për diagnozë dhe trajtim të saktë.</p>";
    return medicalContent;
  }

  private static Set<TagDTO> getTagContent(int i) {
    // Create sample tags for the article
    Set<TagDTO> tags = new HashSet<>();

    boolean isPlural = (i != 1);
    TagDTO tag1 = new TagDTO();
    if (isPlural) {
      tag1.setName("Gjinekologji " + i);

      TagDTO tag2 = new TagDTO();
      tag2.setName("Dermatologji  " + i);

      tags.add(tag1);
      tags.add(tag2);

    } else {
      tag1.setName("Dhimbje " + i);

      tags.add(tag1);
    }

    return tags;
  }
}
