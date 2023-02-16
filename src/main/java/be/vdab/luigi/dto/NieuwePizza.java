package be.vdab.luigi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record NieuwePizza(@NotBlank String naam, @NotNull @PositiveOrZero BigDecimal prijs) {
}
