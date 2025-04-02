package ma.snrt.snrt.Services;


import ma.snrt.snrt.Entities.Roles;
import ma.snrt.snrt.Repositories.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolesService {
    @Autowired
    private RolesRepository rolesRepository;

    public Roles getRole(Long id) {
        Optional<Roles> role = rolesRepository.findById(id);
        if(role.isPresent()) {
            return role.get();
        }
        else {
            throw new RuntimeException("Role not found");
        }
    }

    public List<Roles> getRoles() {
        return rolesRepository.findAll();
    }

    public Roles addRole(Roles role) {
        return rolesRepository.save(role);
    }

    public String deleteRole(Long id) {
        Roles role = getRole(id);
        rolesRepository.delete(role);
        return "Role deleted";
    }

    public Roles updateRole(Long id, Roles role) {
        Roles existingRole = getRole(role.getId());
        existingRole.setRole(role.getRole());
        return rolesRepository.save(existingRole);
    }
}
