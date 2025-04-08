package com.allMighty.data_initalizer;

import static com.allMighty.data_initalizer.DataInitializerUtils.*;

import com.allMighty.business_logic_domain.analysis.AnalysisService;
import com.allMighty.business_logic_domain.analysis.dto.AnalysisDTO;
import com.allMighty.business_logic_domain.article.ArticleDTO;
import com.allMighty.business_logic_domain.article.ArticleService;
import com.allMighty.business_logic_domain.event.EventDTO;
import com.allMighty.business_logic_domain.event.EventService;
import com.allMighty.business_logic_domain.medical_service.MedicalServiceDTO;
import com.allMighty.business_logic_domain.medical_service.MedicalServiceService;
import com.allMighty.config.security.person.repository.PersonEntityRepository;
import com.allMighty.config.security.person.role.Role;
import com.allMighty.enitity.PersonEntity;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
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
    log.error("Initializing data...[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]");
    insertDummyMainUser();
    insertDummyArticles();
    insertDummyEvents();
    insertDummyAnalysis();
    insertDummyMedicalServices();
    analysisService.createInitialAnalysis();

    log.error("Finished Initializing data...[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]");

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

    List<MedicalServiceDTO> services = generateMedicalServices();
    for (MedicalServiceDTO dto : services) {
      medicalServiceService.createMedicalService(dto);
    }
  }

  private void insertDummyArticles() {

    if (eventService.count() != 0) {
      return;
    }

    List<ArticleDTO> articles = generateArticles();
    for (ArticleDTO dto : articles) {
      articleService.createArticle(dto);
    }
  }

  private void insertDummyEvents() {
    if (eventService.count() != 0) {
      return;
    }

    List<EventDTO> eventDTOS = generateEvents();
    for (EventDTO eventDTO : eventDTOS) {
      eventService.createEvent(eventDTO);
    }
  }

  private void insertDummyAnalysis() {

    if (analysisService.count() != 0) {
      return;
    }

    List<AnalysisDTO> analysisDTOS = generateAnalysis();
    for (AnalysisDTO dto : analysisDTOS) {
      analysisService.createAnalysis(dto);
    }
  }
}
