package com.allMighty.client;

public interface UrlProperty extends ParamProperty {
    String ROOT_PATH = "/v1/";
    String ID_PATH = "/{id}";

    interface Article{
        String PATH = ROOT_PATH + "/articles";
        String BY_ID = ROOT_PATH + ID_PATH;
    }
}
