package ru.bisha.easycrm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class CategoryDto {

    private String id;
    private String name;
}