package be.vdab.luigi.services;

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.repositories.PizzaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PizzaService {
    private final PizzaRepository pizzaRepository;

    public PizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    public long findAantal() {
        return pizzaRepository.findAantal();
    }

    public Optional<Pizza> findById(long id) {
        return pizzaRepository.findById(id);
    }
    public List<Pizza> findAll() {
        return pizzaRepository.findAll();
    }
}
