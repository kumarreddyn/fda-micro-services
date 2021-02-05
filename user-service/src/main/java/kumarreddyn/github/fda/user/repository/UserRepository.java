package kumarreddyn.github.fda.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kumarreddyn.github.fda.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
