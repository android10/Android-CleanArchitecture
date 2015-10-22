package com.fernandocejas.android10.sample.data.entity.mapper;

import com.fernandocejas.android10.sample.data.entity.Entity;
import com.google.gson.JsonSyntaxException;
import java.util.List;

/**
 * @author maciej@goraczka.com
 */
public interface EntityJsonMapper<T extends Entity> {

  T jsonToEntity(String userJsonResponse) throws JsonSyntaxException;

  List<T> jsonToEntityList(String userListJsonResponse)
      throws JsonSyntaxException;
}
