package com.omkar.assignment1.Verticles.Retrieve;


import com.omkar.assignment1.Verticles.Employee;
import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchList {

    List<Employee>assets;

    JsonObject toJsonObject(){return JsonObject.mapFrom(this);}
}
