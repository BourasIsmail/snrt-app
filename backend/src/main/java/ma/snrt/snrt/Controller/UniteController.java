package ma.snrt.snrt.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.snrt.snrt.Entities.Unite;
import ma.snrt.snrt.Services.UniteService;

@RestController
@RequestMapping("/unite")
@RequiredArgsConstructor
@Tag(name = "Unite",
        description = "This API provides the capability to manipulate Unite from a Unite Repository")
public class UniteController {

    private final UniteService uniteService;

    //Endpoint to list all unites
    @GetMapping("/list")
    public ResponseEntity<List<Unite>>  listUnites() {
        List<Unite> unites = uniteService.getUnites();
        return ResponseEntity.ok(unites);
    }

    //Endpoint to get a unite by id
    @GetMapping("/get/{id}")
    public ResponseEntity<Unite> getUnite(@PathVariable Long id) {
        Unite unite = uniteService.getUnite(id);
        return ResponseEntity.ok(unite);
    }

    //Endpoint to add a new unite
    @PostMapping("/add")
    public ResponseEntity<Unite> addUnite(@RequestBody Unite unite) {
        try {
            Unite newUnite = uniteService.addUnite(unite);
            return ResponseEntity.ok(newUnite);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //Endpoint to delete a unite by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUnite(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(uniteService.deleteUnite(id));
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting unite: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Endpoint to update a unite by id
    @PutMapping("/update/{id}")
    public ResponseEntity<Unite> updateUnite(@PathVariable Long id,@RequestBody Unite unite) {
        Unite updatedUnite = uniteService.updateUnite(id, unite);
        return ResponseEntity.ok(updatedUnite);
    }

    //Endpoint to get unites by user
    @GetMapping("/getByUser/{id}")
    public ResponseEntity<List<Unite>> getUnitesByUser(@PathVariable Long id) {
        List<Unite> unites = uniteService.getUnitesByUser(id);
        return ResponseEntity.ok(unites);
    }



}
