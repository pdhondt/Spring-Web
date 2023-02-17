package be.vdab.luigi.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PizzaPrijs {
    private final BigDecimal prijs;
    private final LocalDateTime vanaf;
    private final long pizzaId;

    public PizzaPrijs(BigDecimal prijs, LocalDateTime vanaf, long pizzaId) {
        this.prijs = prijs;
        this.vanaf = vanaf;
        this.pizzaId = pizzaId;
    }
    public PizzaPrijs(BigDecimal prijs, long pizzaId) {
        this(prijs, LocalDateTime.now(), pizzaId);
    }

    public BigDecimal getPrijs() {
        return prijs;
    }

    public LocalDateTime getVanaf() {
        return vanaf;
    }

    public long getPizzaId() {
        return pizzaId;
    }
}
