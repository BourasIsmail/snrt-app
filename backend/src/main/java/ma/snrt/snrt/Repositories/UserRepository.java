package ma.snrt.snrt.Repositories;

import ma.snrt.snrt.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByName(String name);

    List<User> findByUniteId(Long uniteId);
}
