package br.com.berne.calvinus.serviceplan;

import static org.junit.Assert.assertNotNull;

import br.com.berne.calvinus.CalvinusApplicationTests;
import br.com.berne.calvinus.serviceplan.model.Section;
import br.com.berne.calvinus.serviceplan.model.SectionEntry;
import br.com.berne.calvinus.serviceplan.model.Template;
import br.com.berne.calvinus.serviceplan.repository.TemplateRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Date;

public class TemplateRepositoryTest extends CalvinusApplicationTests {

  @Autowired
  private TemplateRepository templateRepository;

  @Test
  public void testSaveTemplate() {

    SectionEntry entry = new SectionEntry();
    entry.setAction("Chegada da banda");
    entry.setDescription("Ligar a mesa de som e iluminacão");

    Section section = new Section();
    section.setName("Pré-culto");
    section.setEntries(Collections.singletonList(entry));

    Template template = new Template();
    template.setName("Padrão");
    template.setCreatedAt(new Date());
    template.setCreatedBy("anyone");
    template.setSections(Collections.singletonList(section));

    template = templateRepository.save(template);

    assertNotNull(template);

  }

}
