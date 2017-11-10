package br.com.berne.calvinus.serviceplan.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "sp_planning")
public class ServicePlan {

  private String serie;
  private String messageTitle;
  private String messageBody;
  private Date date;
  private Integer duration; // minutes
  private String comments;


}
