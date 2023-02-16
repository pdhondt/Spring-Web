package be.vdab.luigi.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@Sql("/pizzas.sql")
@AutoConfigureMockMvc
class PizzaControllerTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final static String PIZZAS = "pizzas";
    private final MockMvc mockMvc;
    PizzaControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }
    private long idVanTestPizza() {
        return jdbcTemplate.queryForObject(
                "select id from pizzas where naam = 'test1'", Long.class);
    }
    @Test
    void findAantal() throws Exception {
        mockMvc.perform(get("/pizzas/aantal"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$").value(countRowsInTable(PIZZAS)));
    }
    @Test
    void findById() throws Exception {
        var id = idVanTestPizza();
        mockMvc.perform(get("/pizzas/{id}", id))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("id").value(id),
                        jsonPath("naam").value("test1"));
    }
    @Test
    void findByIdGeeftNotFoundBijEenOnbestaandePizza() throws Exception {
        mockMvc.perform(get("/pizzas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }
    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/pizzas"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("length()").value(countRowsInTable(PIZZAS)));
    }
    @Test
    void findByNaamBevat() throws Exception {
        mockMvc.perform(get("/pizzas")
                .param("naamBevat", "test"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("length()").value(
                                countRowsInTableWhere(PIZZAS, "naam like '%test%'")));
    }
    @Test
    void findByPrijsBetween() throws Exception {
        mockMvc.perform(get("/pizzas")
                .param("vanPrijs", "10")
                .param("totPrijs", "20"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("length()").value(
                                countRowsInTableWhere(PIZZAS, "prijs between 10 and 20")));
    }
    @Test
    void deleteVerwijdertPizza() throws Exception {
        var id = idVanTestPizza();
        mockMvc.perform(delete("/pizzas/{id}", id))
                .andExpect(status().isOk());
        assertThat(countRowsInTableWhere(PIZZAS, "id = " + id)).isZero();
    }
}
