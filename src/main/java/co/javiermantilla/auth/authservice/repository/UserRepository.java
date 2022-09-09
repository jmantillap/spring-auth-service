package co.javiermantilla.auth.authservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.javiermantilla.auth.authservice.entity.User;

@Repository
public interface  UserRepository extends JpaRepository<User, Long> {
	public User findByUserName(String username);
}
