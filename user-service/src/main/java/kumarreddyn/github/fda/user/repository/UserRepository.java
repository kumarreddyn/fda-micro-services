package kumarreddyn.github.fda.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kumarreddyn.github.fda.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByMobileNumberAndPassword(String mobileNumber, String password);
	
}
