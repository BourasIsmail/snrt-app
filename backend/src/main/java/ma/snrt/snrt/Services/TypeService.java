package ma.snrt.snrt.Services;

import ma.snrt.snrt.Entities.Type;
import ma.snrt.snrt.Repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;

    public List<Type> getTypes() {
        return typeRepository.findAll();
    }

    public Type getType(Long id) {
        Optional<Type> type = typeRepository.findById(id);
        if (type.isPresent()) {
            return type.get();
        } else {
            throw new RuntimeException("Type not found");
        }
    }

    public Type addType(Type type) {
        return typeRepository.save(type);
    }

    public String deleteType(Long id) {
        Type type = getType(id);
        typeRepository.delete(type);
        return "Type deleted";
    }

    public Type updateType(Long id, Type type) {
        Type existingType = getType(id);
        existingType.setNom(type.getNom());
        return typeRepository.save(existingType);
    }

    public Type getTypeByNom(String nom) {
        Optional<Type> type = Optional.ofNullable(typeRepository.findByNom(nom));
        if (type.isPresent()) {
            return type.get();
        } else {
            throw new RuntimeException("Type not found");
        }
    }

}
