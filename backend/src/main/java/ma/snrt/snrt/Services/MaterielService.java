package ma.snrt.snrt.Services;

import ma.snrt.snrt.Entities.Materiel;
import ma.snrt.snrt.Entities.Type;
import ma.snrt.snrt.Entities.Unite;
import ma.snrt.snrt.Repositories.MaterielRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterielService {
    @Autowired
    private MaterielRepo materielRepository;

    @Autowired
    private UniteService uniteService;

    @Autowired
    private TypeService typeService;

    public List<Materiel> getMateriels() {
        return materielRepository.findAll();
    }

    public List<Materiel> getMaterielsByUnite(Long id) {
        return materielRepository.findMaterielByUnite(id);
    }

    public Materiel getMateriel(Long id) {
        Optional<Materiel> materiel = materielRepository.findById(id);
        if (materiel.isPresent()) {
            return materiel.get();
        } else {
            throw new RuntimeException("Materiel not found");
        }
    }

    public Materiel addMateriel(Materiel materiel) {
        Unite unite = uniteService.getUnite(materiel.getUnite().getId());
        Type type = typeService.getType(materiel.getType().getId());
        materiel.setUnite(unite);
        materiel.setType(type);
        return materielRepository.save(materiel);
    }

    public Materiel updateMateriel(Long id, Materiel materiel) {
        Materiel existingMateriel = getMateriel(id);
        existingMateriel.setDesignation(materiel.getDesignation());
        existingMateriel.setMarque(materiel.getMarque());
        existingMateriel.setModel(materiel.getModel());
        existingMateriel.setQuantity(materiel.getQuantity());
        existingMateriel.setEtat(materiel.getEtat());
        existingMateriel.setSerialNumber(materiel.getSerialNumber());
        existingMateriel.setNumMarche(materiel.getNumMarche());
        existingMateriel.setSelected(materiel.isSelected());
        existingMateriel.setUnite(materiel.getUnite());
        existingMateriel.setType(materiel.getType());
        return materielRepository.save(existingMateriel);
    }

    public String deleteMateriel(Long id) {
        Materiel materiel = getMateriel(id);
        materielRepository.delete(materiel);
        return "Materiel deleted";
    }

    public List<Materiel> addMultipleMateriels(List<Materiel> materiels) {
        try {
            for (Materiel materiel : materiels) {
                Unite unite = uniteService.getUnite(materiel.getUnite().getId());
                Type type;
                try {
                    type = typeService.getTypeByNom(materiel.getType().getNom());
                } catch (RuntimeException e) {
                    type = null; // Set type to null if not found
                }
                materiel.setUnite(unite);
                materiel.setType(type);

                // Check if materiel with the same serial number already exists
                Optional<Materiel> existingMaterielOpt = materielRepository.getBySerialNumber(materiel.getSerialNumber());
                if (existingMaterielOpt.isPresent()) {
                    Materiel existingMateriel = existingMaterielOpt.get();
                    existingMateriel.setDesignation(materiel.getDesignation());
                    existingMateriel.setMarque(materiel.getMarque());
                    existingMateriel.setModel(materiel.getModel());
                    existingMateriel.setQuantity(materiel.getQuantity());
                    existingMateriel.setNumMarche(materiel.getNumMarche());
                    existingMateriel.setEtat(materiel.getEtat());
                    existingMateriel.setSelected(materiel.isSelected());
                    existingMateriel.setUnite(unite);
                    existingMateriel.setType(type);
                    materielRepository.save(existingMateriel);
                } else {
                    materielRepository.save(materiel);
                }
            }
            return materielRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error adding multiple materiels", e);
        }
    }

    public int getMaterielCount() {
        return materielRepository.findAll().size();
    }

    public Object getMaterielByType() {
        return materielRepository.countMaterielByType();
    }

    public Object getMaterielByUnite() {
        return materielRepository.countMaterielByUnite();
    }

    public Object getMaterielByUniteAndType() {
        return materielRepository.countMaterielByUniteAndType();
    }

}
