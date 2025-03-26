package com.allMighty.client;

public interface UrlProperty extends ParamProperty {
  String ROOT_PATH = "/v1";
  String ID_PATH = "/{id}";

  interface Auth {
    String PATH = ROOT_PATH + "/auth";
    String AUTHENTICATE = "/authenticate";
  }

  interface Article {
    String PATH = ROOT_PATH + "/articles";
  }

  interface Event {
    String PATH = ROOT_PATH + "/events";
  }

  interface Image {
    String PATH = ROOT_PATH + "/images";
  }

  interface Email {
    String PATH = ROOT_PATH + "/emails";
  }

  interface Analysis {
    String PATH = ROOT_PATH + "/analyses";
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
