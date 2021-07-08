package br.com.caelum.carangobom.user;

import br.com.caelum.carangobom.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}