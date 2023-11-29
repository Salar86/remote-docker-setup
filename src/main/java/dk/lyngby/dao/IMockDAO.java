package dk.lyngby.dao;

import dk.lyngby.exception.ApiException;

import java.util.List;

public interface IMockDAO<DTO, ID> {
    DTO read(ID id) throws ApiException;
    List<DTO> readAll();
    DTO create(DTO dto);
    DTO update(ID id, DTO dto);
    DTO delete(ID id);
}
