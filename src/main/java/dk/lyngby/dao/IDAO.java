package dk.lyngby.dao;


import dk.lyngby.exception.ApiException;

import java.util.List;

public interface IDAO<Entity, ID> {

    Entity read(ID id) throws ApiException;
    List<Entity> readAll();
    Entity create(Entity entity);
    Entity update(ID id, Entity entity);
    Entity delete(ID id);


}
