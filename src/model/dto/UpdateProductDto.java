package model.dto;

import java.sql.Date;

public record UpdateProductDto(
        String pName,
        Date expiredDate
) {
}
