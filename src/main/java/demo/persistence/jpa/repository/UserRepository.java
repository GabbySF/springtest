package demo.persistence.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import demo.persistence.jpa.entity.User;

public interface UserRepository extends CrudRepository<User,Long>{

	User findByUid(String uid);
}
