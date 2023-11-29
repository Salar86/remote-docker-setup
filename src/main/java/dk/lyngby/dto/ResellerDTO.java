package dk.lyngby.dto;

import dk.lyngby.model.Reseller;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResellerDTO {
    private int resellerId;
    private String resellerName;
    private String resellerAddress;
    private String resellerPhone;

    public ResellerDTO(Reseller reseller) {
        this.resellerId = reseller.getId();
        this.resellerName = reseller.getName();
        this.resellerAddress = reseller.getAddress();
        this.resellerPhone = reseller.getPhone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResellerDTO that)) return false;
        return getResellerId() == that.getResellerId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResellerId());
    }


}
