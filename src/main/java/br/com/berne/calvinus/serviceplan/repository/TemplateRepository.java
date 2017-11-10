package br.com.berne.calvinus.serviceplan.repository;

import br.com.berne.calvinus.serviceplan.model.Template;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TemplateRepository extends MongoRepository<Template, String> {
}
