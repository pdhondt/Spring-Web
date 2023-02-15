package be.vdab.luigi.repositories;

import be.vdab.luigi.domain.Pizza;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class PizzaRepository {
    private final JdbcTemplate template;
    private final RowMapper<Pizza> pizzaMapper = (rs, rowNum) ->
            new Pizza(rs.getLong("id"), rs.getString("naam"),
                    rs.getBigDecimal("prijs"), rs.getBigDecimal("winst"));

    public PizzaRepository(JdbcTemplate template) {
        this.template = template;
    }

    public long findAantal() {
        var sql = """
                select count(*)
                from pizzas
                """;
        return template.queryForObject(sql, Long.class);
    }

    public Optional<Pizza> findById(long id) {
        try {
            var sql = """
                    select id, naam, prijs, winst
                    from pizzas
                    where id = ?
                    """;
            return Optional.of(template.queryForObject(sql, pizzaMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }
    public List<Pizza> findAll() {
        var sql = """
                select id, naam, prijs, winst
                from pizzas
                order by naam
                """;
        return template.query(sql, pizzaMapper);
    }
    public List<Pizza> findByNaamBevat(String woord) {
        var sql = """
                select id, naam, prijs, winst
                from pizzas
                where naam like ?
                order by naam
                """;
        return template.query(sql, pizzaMapper, "%" + woord + "%");
    }
    public List<Pizza> findByPrijsTussen(BigDecimal van, BigDecimal tot) {
        var sql = """
                select id, naam, prijs, winst
                from pizzas
                where prijs between ? and ?
                order by prijs
                """;
        return template.query(sql, pizzaMapper, van, tot);
    }
}
