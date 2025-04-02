package ma.snrt.snrt.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.snrt.snrt.Entities.Roles;
import ma.snrt.snrt.Services.RolesService;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Tag(name = "Roles",
        description = "This API provides the capability to manipulate Unite from a Role Repository")
public class RolesController {
    @Autowired
    private RolesService rolesService;

    @GetMapping("/list")
    public ResponseEntity<List<Roles>> getRoles() {
        return ResponseEntity.ok(rolesService.getRoles());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Roles> getRole(@PathVariable Long id) {
        return ResponseEntity.ok(rolesService.getRole(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Roles> addRole(@RequestBody Roles role) {
        return ResponseEntity.ok(rolesService.addRole(role));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        return ResponseEntity.ok(rolesService.deleteRole(id));
    }

    public ResponseEntity<Roles> updateRole(@PathVariable Long id,@RequestBody Roles role) {
        return ResponseEntity.ok(rolesService.updateRole(id, role));
    }
}
