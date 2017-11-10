package br.com.berne.calvinus.serviceplan.model;

import java.util.List;

public class Section {

  private String name;
  private List<SectionEntry> entries;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<SectionEntry> getEntries() {
    return entries;
  }

  public void setEntries(List<SectionEntry> entries) {
    this.entries = entries;
  }
}
