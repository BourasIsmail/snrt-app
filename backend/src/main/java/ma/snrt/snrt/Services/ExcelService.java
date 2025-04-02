package ma.snrt.snrt.Services;

import ma.snrt.snrt.Entities.Materiel;
import ma.snrt.snrt.Repositories.MaterielRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

@Service
public class ExcelService {

    @Autowired
    private MaterielRepo materielRepository;
    @Autowired
    private MaterielService materielService;

    private static final Logger logger = LoggerFactory.getLogger(ExcelService.class);


    public void readExcelAndUpdateMateriels(MultipartFile file) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) {
                    continue; // Skip header row
                }

                Materiel materiel = new Materiel();
                materiel.setDesignation(getCellValueAsString(row.getCell(0)));
                materiel.setMarque(getCellValueAsString(row.getCell(1)));
                materiel.setModel(getCellValueAsString(row.getCell(2)));
                materiel.setSerialNumber(getCellValueAsString(row.getCell(3)));
                materiel.setQuantity((int) row.getCell(4).getNumericCellValue());
                materiel.setEtat(getCellValueAsString(row.getCell(6)));
                materiel.setSelected(getCellValueAsBoolean(row.getCell(7)));

                Materiel existingMateriel = materielRepository.findBySerialNumber(materiel.getSerialNumber());
                if (existingMateriel != null) {
                    materiel.setId(existingMateriel.getId());
                    materielService.updateMateriel(existingMateriel.getId(), materiel);
                } else {
                    materielService.addMateriel(materiel);
                }
            }
        } catch (Exception e) {
            logger.error("Error reading Excel file and updating materiels: {}", e.getMessage(), e);
            throw new IOException("Error processing Excel file", e);
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }
        return cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : cell.toString();
    }

    private boolean getCellValueAsBoolean(Cell cell) {
        if (cell == null) {
            return false;
        }
        return cell.getCellType() == CellType.BOOLEAN ? cell.getBooleanCellValue() : Boolean.parseBoolean(cell.toString());
    }
}