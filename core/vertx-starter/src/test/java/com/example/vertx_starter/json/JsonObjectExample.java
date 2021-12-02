package com.example.vertx_starter.json;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonObjectExample {

  @Test
  void jsonObjectCanBeMapped(){
    final JsonObject myJsonObject = new JsonObject();
    myJsonObject.put("id",1);
    myJsonObject.put("name","Junglee");
    myJsonObject.put("loves_vertx",true);

    final String encoded = myJsonObject.encode();
    assertEquals("{\"id\":1,\"name\":\"Junglee\",\"loves_vertx\":true}",encoded);

    final JsonObject decodedJsonObject = new JsonObject(encoded);
    assertEquals(myJsonObject,decodedJsonObject);

  }

  @Test
  void jsonObjectCanBeCreatedFromMap(){
    final Map<String,Object>myMap = new HashMap<>();
    myMap.put("id",1);
    myMap.put("name","Junglee");
    myMap.put("loves_vertx",true);

    final JsonObject asJsonObject = new JsonObject(myMap);
    assertEquals(myMap,asJsonObject.getMap());
    assertEquals(1,asJsonObject.getInteger("id"));
    assertEquals("Junglee",asJsonObject.getString("name"));
    assertEquals(true,asJsonObject.getBoolean("loves_vertx"));

  }

  @Test
  void jsonArrayCanBeMapped(){
    final JsonArray myJsonArray = new JsonArray();
    myJsonArray
      .add(new JsonObject().put("id",1))
      .add(new JsonObject().put("id",2))
      .add(new JsonObject().put("id",3))
      .add("randomValue");

    assertEquals("[{\"id\":1},{\"id\":2},{\"id\":3},\"randomValue\"]",myJsonArray.encode());
  }


  @Test
  void canMapJavaObject(){
    final Person person = new Person(1,"junglee",true);
    final JsonObject junglee = JsonObject.mapFrom(person);

    assertEquals(person.getId(),junglee.getInteger("id"));
    assertEquals(person.getName(),junglee.getString("name"));
    assertEquals(person.isLoves_vertx(),junglee.getBoolean("loves_vertx"));

    final Person person2 = junglee.mapTo(Person.class);
    assertEquals(person.getId(),person2.getId());
    assertEquals(person.getName(),person2.getName());
    assertEquals(person.isLoves_vertx(),person2.isLoves_vertx());


  }
}
