package ma.snrt.snrt.Repositories;

import ma.snrt.snrt.Entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {

    Type findByNom(String nom);

}
