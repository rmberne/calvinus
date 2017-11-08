package br.com.berne.calvinus.security.repository;

import br.com.berne.calvinus.security.model.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoUserRepository extends MongoRepository<MongoUser, String> {

  MongoUser findByUsername(String username);

}
