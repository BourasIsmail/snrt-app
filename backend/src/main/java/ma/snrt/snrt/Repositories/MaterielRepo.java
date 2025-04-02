package ma.snrt.snrt.Repositories;

import ma.snrt.snrt.Entities.Materiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterielRepo extends JpaRepository<Materiel, Long> {
    Materiel findByDesignation(String designation);

    @Query("SELECT m FROM Materiel m where m.unite.id = :id")
    List<Materiel> findMaterielByUnite(@Param("id") Long id);

    @Query("SELECT m.type.nom, COUNT(m) FROM Materiel m GROUP BY m.type.nom")
    List<Object[]> countMaterielByType();

    @Query("SELECT m.unite.nom, COUNT(m) FROM Materiel m GROUP BY m.unite.nom")
    List<Object[]> countMaterielByUnite();

    @Query("SELECT m.unite.nom, m.type.nom, COUNT(m) FROM Materiel m GROUP BY m.unite.nom, m.type.nom")
    List<Object[]> countMaterielByUniteAndType();

    Materiel findBySerialNumber(String serialNumber);
    Optional<Materiel> getBySerialNumber(String serialNumber);

}
