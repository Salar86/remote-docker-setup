package dk.lyngby.dao.impl;

import dk.lyngby.dao.IMockDAO;

import java.util.ArrayList;
import java.util.List;

public abstract class AMockDAO<DTO, ID> implements IMockDAO<DTO, ID> {
    protected List<DTO> data = new ArrayList<>();

    @Override
    public List<DTO> readAll() {
        return data;
    }

    @Override
    public DTO read(ID id) {
        return data.stream()
                .filter(item -> id.equals(getId(item)))
                .findFirst()
                .orElse(null);
    }

    @Override
    public DTO create(DTO dto) {
        data.add(dto);
        return dto;
    }

    @Override
    public DTO update(ID id, DTO dto) {
        for (int i = 0; i < data.size(); i++) {
            DTO item = data.get(i);
            if (id.equals(getId(item))) {
                data.set(i, dto);
                return dto;
            }
        }
        return null;
    }

    @Override
    public DTO delete(ID id) {
        for (DTO item : data) {
            if (id.equals(getId(item))) {
                data.remove(item);
                return item;
            }
        }
        return null;
    }

    // Abstract method to get the ID from the DTO
    protected abstract ID getId(DTO dto);
}
