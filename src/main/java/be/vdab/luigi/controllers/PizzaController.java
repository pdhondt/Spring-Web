package be.vdab.luigi.controllers;

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.domain.PizzaPrijs;
import be.vdab.luigi.dto.NieuwePizza;
import be.vdab.luigi.exceptions.PizzaNietGevondenException;
import be.vdab.luigi.services.PizzaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.stream.Stream;

@RestController
class PizzaController {
    private final PizzaService pizzaService;

    PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }
    private record IdNaamPrijs(long id, String naam, BigDecimal prijs) {
        IdNaamPrijs(Pizza pizza) {
            this(pizza.getId(), pizza.getNaam(), pizza.getPrijs());
        }
    }
    private record PrijsWijziging(@NotNull @PositiveOrZero BigDecimal prijs) {}

    @GetMapping("pizzas/aantal")
    long findAantal() {
        return pizzaService.findAantal();
    }
    @GetMapping("pizzas/{id}")
    IdNaamPrijs findById(@PathVariable long id) {
        return pizzaService.findById(id)
                .map(pizza -> new IdNaamPrijs(pizza))
                .orElseThrow(() -> new PizzaNietGevondenException(id));
    }
    @GetMapping("pizzas")
    Stream<IdNaamPrijs> findAll() {
        return pizzaService.findAll()
                .stream()
                .map(pizza -> new IdNaamPrijs(pizza));
    }
    @GetMapping(value = "pizzas", params = "naamBevat")
    Stream<IdNaamPrijs> findByNaamBevat(String naamBevat) {
        return pizzaService.findByNaamBevat(naamBevat)
                .stream()
                .map(pizza -> new IdNaamPrijs(pizza));
    }
    @GetMapping(value = "pizzas", params = {"vanPrijs", "totPrijs"})
    Stream<IdNaamPrijs> findByPrijsTussen(BigDecimal vanPrijs, BigDecimal totPrijs) {
        return pizzaService.findByPrijsTussen(vanPrijs, totPrijs)
                .stream()
                .map(pizza -> new IdNaamPrijs(pizza));
    }
    @DeleteMapping("pizzas/{id}")
    void delete(@PathVariable long id) {
        pizzaService.delete(id);
    }
    @PostMapping("pizzas")
    long create(@RequestBody @Valid NieuwePizza nieuwePizza) {
        var id = pizzaService.create(nieuwePizza);
        return id;
    }
    @PatchMapping("pizzas/{id}/prijs")
    void updatePrijs(@PathVariable long id,
                     @RequestBody @Valid PrijsWijziging wijziging) {
        pizzaService.updatePrijs(new PizzaPrijs(wijziging.prijs, id));
    }
}
