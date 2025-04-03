package com.allMighty.business_logic_domain.export;

import com.allMighty.business_logic_domain.email.EmailDetailDTO;
import com.allMighty.enitity.EmailEntity;
import com.allMighty.global_operation.exception_management.exception.ExcelFailException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportService {


    public byte[] generateSubscribersEmailExcel(List<EmailDetailDTO> emailList) {
        Workbook workbook = new XSSFWorkbook();
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Sheet sheet = workbook.createSheet("Subscribers Email");
        prepareColumns(sheet, headerStyle);
        addValuesToColumns(emailList, sheet);

        return returnFileToByteFormat(workbook);
    }


    private static byte[] returnFileToByteFormat(Workbook workbook) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            workbook.write(baos);
            workbook.close();
        } catch (IOException e) {
            throw new ExcelFailException("Excel generating failed", e);
        }
        return baos.toByteArray();
    }

    private void addValuesToColumns(List<EmailDetailDTO> emailList, Sheet sheet) {
        int rowNum = 1;
        for (EmailDetailDTO email : emailList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(email.getEmail());
        }
    }

    private void prepareColumns(Sheet sheet, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(0);
        Cell cell = headerRow.createCell(0);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Email Address");
        sheet.setColumnWidth(0, 50 * 256);
    }
}
