package br.com.berne.calvinus.security.service;

import br.com.berne.calvinus.security.model.MongoUser;
import br.com.berne.calvinus.security.repository.MongoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MongoUserService implements UserDetailsService {

  private final MongoUserRepository mongoUserRepository;

  @Autowired
  public MongoUserService(MongoUserRepository mongoUserRepository) {
    this.mongoUserRepository = mongoUserRepository;
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    MongoUser mongoUser = mongoUserRepository.findByUsername(username);
    if (mongoUser != null) {
      return new User(mongoUser.getUsername(), mongoUser.getPassword(), Collections.emptyList());
    }

    throw new UsernameNotFoundException(username);
  }
}
