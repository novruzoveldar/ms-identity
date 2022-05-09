package com.guavapay.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FinishedRegistrationDto {

    private String token;
}
