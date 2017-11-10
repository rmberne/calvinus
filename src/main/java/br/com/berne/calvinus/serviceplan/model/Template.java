package br.com.berne.calvinus.serviceplan.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "sp_templates")
public class Template {

  @Id
  private String name;
  private String createdBy;
  private Date createdAt;

  private List<Section> sections;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public List<Section> getSections() {
    return sections;
  }

  public void setSections(List<Section> sections) {
    this.sections = sections;
  }
}
