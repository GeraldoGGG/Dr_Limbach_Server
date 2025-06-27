package com.allMighty.business_logic_domain.export;

import com.allMighty.business_logic_domain.analysis.ExcelAnalysisDataDTO;
import com.allMighty.business_logic_domain.email.EmailDetailDTO;
import com.allMighty.global_operation.exception_management.exception.ExcelFailException;

import java.io.*;
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
    try (Workbook workbook = new XSSFWorkbook()) {
      CellStyle headerStyle = workbook.createCellStyle();
      headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
      headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

      Sheet sheet = workbook.createSheet("Subscribers Email");
      prepareColumns(sheet, headerStyle);
      addValuesToColumns(emailList, sheet);

      return returnFileToByteFormat(workbook);
    } catch (IOException e) {
      throw new ExcelFailException("Excel generation failed", e);
    }
  }
  private static byte[] returnFileToByteFormat(Workbook workbook) throws IOException {
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      workbook.write(baos);
      return baos.toByteArray();
    }
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
    try (InputStream excelStream = getClass().getClassLoader().getResourceAsStream("initialAnalysisData/db-v1.xlsx")) {

      if (excelStream == null) {
        throw new FileNotFoundException("Resource not found: initialAnalysisData/db-v1.xlsx");
      }

      try (Workbook workbook = new XSSFWorkbook(excelStream)) {
        Sheet sheet = workbook.getSheetAt(0);
        List<ExcelAnalysisDataDTO> dtoList = new ArrayList<>();

        for (Row row : sheet) {
          if (row.getRowNum() == 0) continue; // Skip header row

          ExcelAnalysisDataDTO dto = new ExcelAnalysisDataDTO();

          dto.setEmriAnalizes(getCellValue(row, 0));
          dto.setSinonimi(getCellValue(row, 1));
          dto.setKategoria(getCellValue(row, 2));
          dto.setMostra(getCellValue(row, 3));
          dto.setPreanalitika(getCellValue(row, 4));
          dto.setMetoda(getCellValue(row, 5));
          dto.setIndikacioniKlinik(getCellValue(row, 6));
          dto.setInterpretimiIRrezultatit(getCellValue(row, 7));
          dto.setAkredituarNgaISO15189(getCellValue(row, 8));

          dtoList.add(dto);
        }

        return dtoList;
      }

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
