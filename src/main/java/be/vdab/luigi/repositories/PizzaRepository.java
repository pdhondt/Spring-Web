package be.vdab.luigi.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PizzaRepository {
    private final JdbcTemplate template;

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
}
