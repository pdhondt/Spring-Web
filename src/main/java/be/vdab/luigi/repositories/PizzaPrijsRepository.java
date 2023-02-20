package be.vdab.luigi.repositories;

import be.vdab.luigi.domain.PizzaPrijs;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PizzaPrijsRepository {
    private final JdbcTemplate template;
    private final RowMapper<PizzaPrijs> pizzaPrijsMapper = (rs, rowNum) ->
            new PizzaPrijs(rs.getBigDecimal("prijs"), rs.getObject("vanaf", LocalDateTime.class),
                    rs.getLong("pizzaId"));

    public PizzaPrijsRepository(JdbcTemplate template) {
        this.template = template;
    }
    public void create(PizzaPrijs pizzaPrijs) {
        var sql = """
                insert into pizzaprijzen(prijs, vanaf, pizzaId)
                values(?, ?, ?)
                """;
        template.update(sql, pizzaPrijs.getPrijs(), pizzaPrijs.getVanaf(), pizzaPrijs.getPizzaId());
    }
    public List<PizzaPrijs> findByPizzaId(long pizzaId) {
        var sql = """
                select prijs, vanaf, pizzaId
                from pizzaprijzen
                where pizzaId = ?
                order by vanaf
                """;
        return template.query(sql, pizzaPrijsMapper, pizzaId);
    }
}
