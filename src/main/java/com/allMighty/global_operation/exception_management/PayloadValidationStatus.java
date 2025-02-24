package com.allMighty.global_operation.exception_management;

import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class PayloadValidationStatus {
    private static final String BAD_FORMAT_MESSAGE_FORMAT = "Bad format for field '%s'";
    private static final String INTERNAL_ERROR_MESSAGE_FORMAT = "Internal error while updating '%s'";
    private static final Pattern pattern = Pattern.compile("\\(.*?\\)");


    private List<ResponseMessageDTO> responseErrors;

    private List<ResponseMessageDTO> responseWarnings;


    private ResponseMessageDTO buildResponseMessage(String message) {
        List<String> markers = new ArrayList<>();
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            markers.add(matcher.group());
        }

        StringBuffer code = new StringBuffer();
        if (!markers.isEmpty()) {
            code.append(markers.get(0));
            if (markers.size() > 1) {
                code.append(" ").append(markers.get(1));
            }
        }

        String plainMessage = message.replace(code.toString(), "").trim();
        int index = plainMessage.indexOf(":");
        if (index > 0) {
            String errorCode = plainMessage.substring(0, index);
            code.append(" ").append(errorCode.trim());
            plainMessage = plainMessage.substring(index + 1).trim();
        }

        return new ResponseMessageDTO(null, code.toString(), null, plainMessage.trim());
    }

    public void appendError(String message) {

        if (responseErrors == null) {
            responseErrors = new ArrayList<>();
        }

        ResponseMessageDTO messageDTO = buildResponseMessage(message);
        responseErrors.add(messageDTO);

    }

    public void appendWarning(String message) {

        if (responseWarnings == null) {
            responseWarnings = new ArrayList<>();
        }
        ResponseMessageDTO messageDTO = buildResponseMessage(message);
        responseWarnings.add(messageDTO);
    }

    public void appendError(ResponseMessageDTO messageDTO) {

        if (responseErrors == null) {
            responseErrors = new ArrayList<>();
        }
        responseErrors.add(messageDTO);
    }

    public void appendWarning(ResponseMessageDTO messageDTO) {
        if (responseWarnings == null) {
            responseWarnings = new ArrayList<>();
        }
        responseWarnings.add(messageDTO);
    }

    public void appendError(Integer index, String code, String key, String message) {
        ResponseMessageDTO messageDTO = new ResponseMessageDTO(index, code, key, message);
        appendError(messageDTO);
    }

    public void appendWarning(Integer index, String code, String key, String message) {
        ResponseMessageDTO messageDTO = new ResponseMessageDTO(index, code, key, message);
        appendWarning(messageDTO);
    }

    public boolean isValid() {
        return CollectionUtils.isEmpty(responseErrors);
    }

    public boolean isNotValid() {
        return CollectionUtils.isNotEmpty(responseErrors);
    }


    public void merge(PayloadValidationStatus validationStatus) {
        if (validationStatus == null) {
            return;
        }


        if (CollectionUtils.isNotEmpty(validationStatus.getResponseErrors())) {
            validationStatus.getResponseErrors().forEach(this::appendError);
        }

        if (CollectionUtils.isNotEmpty(validationStatus.getResponseWarnings())) {
            validationStatus.getResponseWarnings().forEach(this::appendWarning);
        }
    }
}