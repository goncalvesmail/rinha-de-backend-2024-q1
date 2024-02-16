package com.goncalvesmail.rinhabackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SaldoDTO(
        Long total,
        @JsonProperty("data_extrato") LocalDateTime dataExtrato,
        Long limite) {
}
