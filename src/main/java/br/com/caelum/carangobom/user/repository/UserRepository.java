package br.com.caelum.carangobom.user.repository;

import br.com.caelum.carangobom.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
