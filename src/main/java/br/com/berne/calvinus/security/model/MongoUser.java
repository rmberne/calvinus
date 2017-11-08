package br.com.berne.calvinus.security.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
public class MongoUser {

  private String username;
  private String email;
  private String password;

}
