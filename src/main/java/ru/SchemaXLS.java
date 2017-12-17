package ru;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class SchemaXLS {
    private static final Logger LOG = LoggerFactory.getLogger(SchemaXLS.class);
    private static SchemaXLS schemaXLS;
    private static Map<String, Map<String, String>> schema = new HashMap();
    private static final String PATHTOSCHEMA = "src/main/resources/schema.xls";

    private SchemaXLS() {
    }

    public static SchemaXLS getSchemaXLS() {
        if (schemaXLS == null) {
            schemaXLS = new SchemaXLS();
            try {
                downloadSchema(PATHTOSCHEMA);
            } catch (IOException e) {
                LOG.error("Файл со схемой совместимости ингредиентов не найден!", e);
            }
            return schemaXLS;
        } else {
            return schemaXLS;
        }

    }

    public Map<String, Map<String, String>> getSchema() {
        return schema;
    }

    private static void downloadSchema(String pathToSchema) throws IOException {
        int tmpVal = 0;
        List<String> listnameIngredient = new ArrayList();
        FileInputStream inputStream = new FileInputStream(new File(pathToSchema));
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            if (tmpVal > 0) {
                Iterator<Cell> cellIterator = row.cellIterator();
                List<String> cellList = new ArrayList();
                Map<String, String> mapNameAndValIngredient = new HashMap();
                String key = "";
                int tmpCellVal = 0;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    CellType cellType = cell.getCellTypeEnum();
                    switch (cellType) {
                        case STRING:
                            if (tmpCellVal == 0) {
                                key = cell.getStringCellValue();
                            } else {
                                mapNameAndValIngredient.put(listnameIngredient.get(tmpCellVal - 1), cell.getStringCellValue());
                                cellList.add(cell.getStringCellValue());
                            }
                            tmpCellVal++;
                            break;
                    }
                }
                schema.put(key, mapNameAndValIngredient);
            } else {
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    CellType cellType = cell.getCellTypeEnum();
                    switch (cellType) {
                        case STRING:
                            listnameIngredient.add(cell.getStringCellValue());
                            break;
                    }
                }
            }
            tmpVal++;
        }
        LOG.info("Schema: " + schema);
        inputStream.close();
    }
}
