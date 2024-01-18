package ru.bisha.easycrm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import ru.bisha.easycrm.db.entity.ServiceStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class ServiceDto {

    private String id;
    private String orderId;
    private Integer qty;
    private String description;
    private BigDecimal price;
    private BigDecimal executorMoney;
    private String executorId;
    private String itemId;
    private Boolean isCustom;
    private ServiceStatus status;
    private LocalDateTime statusUpdatedAt;
}
