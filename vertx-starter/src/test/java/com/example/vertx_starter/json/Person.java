package com.example.vertx_starter.json;

public class Person {

  private Integer id;
  private String name;
  private Boolean loves_vertx;

  public Person(){
    //Default Constructor
  }

  public Person(Integer id, String name, Boolean loves_vertx) {
    this.id = id;
    this.name = name;
    this.loves_vertx = loves_vertx;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

//  public Boolean isLoves_vertx() {
//   return loves_vertx;
//  }

  public Boolean getLoves_vertx() {
    return loves_vertx;
  }

  public void setLoves_vertx(Boolean loves_vertx) {
    this.loves_vertx = loves_vertx;
  }


  public Boolean isLoves_vertx() {
    return loves_vertx;
  }
}
