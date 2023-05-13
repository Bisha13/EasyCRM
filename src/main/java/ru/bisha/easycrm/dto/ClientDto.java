package ru.bisha.easycrm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDto {

    private String id;
    private String name;
    private String phone;
    private String phone2;
    private String address;
    private Integer discount;
    private LocalDateTime createdAt;
}
