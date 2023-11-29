package dk.lyngby.dao.impl;

import dk.lyngby.dto.ResellerDTO;

public class RMockDAO extends AMockDAO<ResellerDTO, Integer> {
    RMockDAO() {
        data.add(new ResellerDTO(1, "Lyngby Plantecenter", "Firskovvej 18", "33212334"));
        data.add(new ResellerDTO(2, "Glostrup Planter", "Tværvej 35", "32233232"));
        data.add(new ResellerDTO(3, "Holbæk Planteskole", "Stenhusvej 49", "59430945"));
    }
    @Override
    protected Integer getId(ResellerDTO resellerDTO) {
        return resellerDTO.getResellerId();
    }
}
