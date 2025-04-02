package ma.snrt.snrt.Controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.snrt.snrt.Entities.Materiel;
import ma.snrt.snrt.Services.ExcelService;
import ma.snrt.snrt.Services.MaterielService;

@RestController
@RequestMapping("/materiel")
@RequiredArgsConstructor
@Tag(name = "Materiel",
        description = "This API provides the capability to manipulate Unite from a Materiel Repository")
public class MaterielController {

    @Autowired
    private MaterielService materielService;

    @Autowired
    private ExcelService excelService;

    private static final Logger logger = LoggerFactory.getLogger(ExcelService.class);


    @GetMapping("/listAll")
    public ResponseEntity<List<Materiel>> listMateriels() {
        return ResponseEntity.ok(materielService.getMateriels());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Materiel> getMateriel(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(materielService.getMateriel(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/byUnite/{id}")
    public ResponseEntity<List<Materiel>> getMaterielsByUnite(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(materielService.getMaterielsByUnite(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addMateriel(@RequestBody Materiel materiel) {
        try {
            Materiel newMateriel = materielService.addMateriel(materiel);
            return new ResponseEntity<>("materiel added successfully with ID: " + newMateriel.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding materiel: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateMateriel(@PathVariable Long id,@RequestBody Materiel materiel) {
        try {
            Materiel newMateriel = materielService.updateMateriel(id, materiel);
            return new ResponseEntity<>("materiel updated successfully with ID: " + newMateriel.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating materiel: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMateriel(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(materielService.deleteMateriel(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        try {
            excelService.readExcelAndUpdateMateriels(file);
            return ResponseEntity.ok("File processed successfully");
        } catch (IOException e) {
            logger.error("Error processing file: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error processing file: " + e.getMessage());
        }
    }

    @PostMapping("/addMultiple")
    public ResponseEntity<List<Materiel>> addMultipleMateriels(@RequestBody List<Materiel> materiels) {
        try {
            List<Materiel> addedMateriels = materielService.addMultipleMateriels(materiels);
            return ResponseEntity.ok(addedMateriels);
        } catch (Exception e) {
            logger.error("Error processing file: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }
}
