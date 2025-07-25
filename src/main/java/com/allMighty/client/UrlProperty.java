package com.allMighty.client;

public interface UrlProperty extends ParamProperty {
  String ROOT_PATH = "/v1";
  String ID_PATH = "/{id}";
  String SIMPLE = "/simple";

  interface Auth {
    String PATH = ROOT_PATH + "/auth";
    String AUTHENTICATE = "/authenticate";
  }

  interface Article {
    String PATH = ROOT_PATH + "/articles";
    String ARTICLE_CATEGORIES = "/categories";
  }

  interface Event {
    String PATH = ROOT_PATH + "/events";
  }

  interface Search {
    String PATH = ROOT_PATH + "/search";
  }

  interface Image {
    String PATH = ROOT_PATH + "/images";
  }

  interface Email {
    String PATH = ROOT_PATH + "/emails";
    String SUBSCRIBE = "/subscribe";
    String EXPORT = "/export";
  }

  interface Analysis {
    String PATH = ROOT_PATH + "/analyses";
  }

  interface AnalysisPackage {
    String PATH = ROOT_PATH + "/packages";
  }

  interface AnalysisCategory {
    String PATH = ROOT_PATH + "/categories";
  }

  interface MedicalService {
    String PATH = ROOT_PATH + "/services";
  }

  interface Tag {
    String PATH = ROOT_PATH + "/tags";
  }

  interface Questionnaire {
    String PATH = ROOT_PATH + "/questionnaires";
  }
}
