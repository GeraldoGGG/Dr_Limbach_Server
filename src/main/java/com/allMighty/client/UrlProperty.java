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
}
