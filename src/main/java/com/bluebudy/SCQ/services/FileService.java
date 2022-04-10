package com.bluebudy.SCQ.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import com.bluebudy.SCQ.domain.Area;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    public List<Area> spreadSheetFileToAreaList(MultipartFile multiPartFile, Date dataInicial, Date dataFinal)
            throws Docx4JException, JAXBException, IOException {
        File file = convert(multiPartFile);
        // System.out.println("Procurando em: " + filePath);
        List<Area> areas = new ArrayList<Area>();
        try {

            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            int rowCount = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (rowCount >= 1) {
                    Iterator<Cell> cellIterator = row.cellIterator();
                    int colCount = 0;
                    Area newArea = new Area();
                    while (cellIterator.hasNext()) {
                        Cell revCell = cellIterator.next();
                        if (colCount == 5) {
                            Double area = revCell.getNumericCellValue();
                            newArea.setArea(area);
                        }
                        if (colCount == 6) {
                            Integer groupArea = Integer.valueOf(revCell.getStringCellValue());
                            newArea.setGroupArea(groupArea);

                        }
                        colCount++;
                    }
                    newArea.setDataInicio(dataInicial);
                    newArea.setDataFim(dataFinal);
                    areas.add(newArea);

                }
                rowCount++;
            }
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areas;
    }

    public File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public File generateGenericSpreadSheet(List<?> notificacoes)
            throws FileNotFoundException, IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Analises não realizadas");
        //Construir Header
        Row header = sheet.createRow(0);
        Field[] fields = notificacoes.get(0).getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            header.createCell(i).setCellValue(fieldName);
        }
        Object[] genericData = buildBidimensionalArray(notificacoes);

        int rowCount = 1;
         
        for (Object gBook : genericData) {
            Row row = sheet.createRow(rowCount);
             Object[] gBookCast = (Object[]) gBook;
            int columnCount = 0;
            for (Object field : gBookCast) {
                Cell cell = row.createCell(columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
                columnCount++;
            } 
            rowCount++; 
        }

        File file = new File("ReportDiarioAnalises.xlsx");
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);

        return file;
    }

    public Object[] buildBidimensionalArray(List<?> objects) {
        List<Object[]> dataSheetCells = objects.stream().map(obj -> {
            List<Field> fields = List.of(obj.getClass().getDeclaredFields());

            Object[] campos = fields.stream().map(field -> {
                field.setAccessible(true);
                try {
                    return field.get(obj);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                    return obj;
                }

            }).toArray();
            return campos;
        }).collect(Collectors.toList());
        return dataSheetCells.toArray();
    }

}
