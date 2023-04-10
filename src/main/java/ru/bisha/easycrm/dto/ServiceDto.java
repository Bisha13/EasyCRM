package ru.bisha.easycrm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class ServiceDto {

    private String id;
    private Integer qty;
    private String description;
    private BigDecimal price;
    private String executorId;
    private String itemId;
    private Boolean isCustom;
}
