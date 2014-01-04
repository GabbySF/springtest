package demo.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import demo.persistence.jpa.entity.User;

public interface UserRepository extends JpaRepository<User,Long>,UserRepositoryCustom{

	User findByUid(String uid);
}
