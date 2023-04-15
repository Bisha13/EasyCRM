package ru.bisha.easycrm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartDto {

    private String partId;
    private String name;
    private BigDecimal purchasePrice;
    private BigDecimal price;
    private Integer qty;
    private String stockId;
    private Boolean isStock;
}
