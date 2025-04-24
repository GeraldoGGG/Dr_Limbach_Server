package com.allMighty.business_logic_domain.export;

import com.allMighty.business_logic_domain.analysis.ExcelAnalysisDataDTO;
import com.allMighty.business_logic_domain.email.EmailDetailDTO;
import com.allMighty.global_operation.exception_management.exception.ExcelFailException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

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

  public List<ExcelAnalysisDataDTO> fetchAnalysisExcel() {
    try {
      // File path to the Excel file
      FileInputStream fis = new FileInputStream("src/main/resources/initialAnalysisData/Template_Motorri_i_kerkimit_me_1000_rreshta.xlsx");

      // Create Workbook instance for .xlsx file
      Workbook workbook = new XSSFWorkbook(fis);

      // Get the first sheet from the workbook
      Sheet sheet = workbook.getSheetAt(0);

      List<ExcelAnalysisDataDTO> dtoList = new ArrayList<>();

      for (Row row : sheet) {
        if (row.getRowNum() == 0) continue; // Skip header row

        ExcelAnalysisDataDTO dto = new ExcelAnalysisDataDTO();

        dto.setAnaliza(getCellValue(row, 0));
        dto.setSinonimi(getCellValue(row, 1));
        dto.setKategoria(getCellValue(row, 2));
        dto.setMostra(getCellValue(row, 3));
        dto.setStabiliteti(getCellValue(row, 4));
        dto.setPreanalitika(getCellValue(row, 5));
        dto.setMetoda(getCellValue(row, 6));
        dto.setIndikacioniKlinik(getCellValue(row, 7));
        dto.setInterpretimiIRrezultatit(getCellValue(row, 8));
        dto.setAkredituarNgaISO15189(getCellValue(row, 9));

        // Add the DTO to the list
        dtoList.add(dto);
      }

      // Close the workbook and file input stream
      workbook.close();
      fis.close();
      return dtoList;
    } catch (IOException e) {
      throw new ExcelFailException("Excel generation failed", e);
    }
  }

  private static String getCellValue(Row row, int columnIndex) {
    Cell cell = row.getCell(columnIndex);
    if (cell == null) {
      return "";
    }

    return switch (cell.getCellType()) {
      case STRING -> cell.getStringCellValue();
      case NUMERIC -> String.valueOf(cell.getNumericCellValue());
      case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
      default -> "";
    };
  }
}
