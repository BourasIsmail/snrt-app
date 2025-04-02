package ma.snrt.snrt.Repositories;

import ma.snrt.snrt.Entities.Unite;
import ma.snrt.snrt.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniteRepository extends JpaRepository<Unite, Long> {
    Unite findByNom(String nom);

    @Query("SELECT u FROM Unite u INNER JOIN User us ON u.id = us.unite.id WHERE u.id = :id")
    List<Unite> findUniteByUser(@Param("id") Long id);


}
