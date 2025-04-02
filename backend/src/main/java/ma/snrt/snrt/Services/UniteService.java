package ma.snrt.snrt.Services;

import ma.snrt.snrt.Entities.Unite;
import ma.snrt.snrt.Repositories.UniteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UniteService {
    private final UniteRepository uniteRepository;

    private final UsersService userService;

    public UniteService(UsersService userService, UniteRepository uniteRepository) {
        this.userService = userService;
        this.uniteRepository = uniteRepository;
    }

    public List<Unite> getUnites() {
        return uniteRepository.findAll();
    }

    public List<Unite> getUnitesByUser(Long id) {
        return uniteRepository.findUniteByUser(id);
    }

    public Unite getUnite(Long id) {
        Optional<Unite> unite = uniteRepository.findById(id);
        if (unite.isPresent()) {
            return unite.get();
        } else {
            throw new RuntimeException("Unite not found");
        }
    }

    public Unite addUnite(Unite unite) {
        return uniteRepository.save(unite);
    }

    public String deleteUnite(Long id) {
        try {
            userService.removeUniteFromUsers(id);
            uniteRepository.deleteById(id);
            return "Unite deleted successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error deleting unite: " + e.getMessage());
        }
    }

    public Unite updateUnite(Long id, Unite unite) {
        Unite existingUnite = getUnite(id);
        existingUnite.setNom(unite.getNom());
        existingUnite.setDescription(unite.getDescription());
        return uniteRepository.save(existingUnite);
    }
}
