package ma.snrt.snrt.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import ma.snrt.snrt.Entities.Type;
import ma.snrt.snrt.Services.TypeService;

@RestController
@RequestMapping("/type")
@RequiredArgsConstructor
@Tag(name = "type",
        description = "This API provides the capability to manipulate type from a Role Repository")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/list")
    public ResponseEntity<List<Type>> getTypes() {
        return ResponseEntity.ok(typeService.getTypes());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Type> getType(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(typeService.getType(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Type> addType(@RequestBody Type type) {
        try {
            return ResponseEntity.ok(typeService.addType(type));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteType(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(typeService.deleteType(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Type> updateType(@PathVariable Long id, @RequestBody Type type) {
        try {
            return ResponseEntity.ok(typeService.updateType(id, type));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
