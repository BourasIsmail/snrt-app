package ma.snrt.snrt.Repositories;

import ma.snrt.snrt.Entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    Roles findByRole(String role);
}
