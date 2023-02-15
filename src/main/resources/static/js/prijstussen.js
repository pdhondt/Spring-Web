"use strict";
import {byId, toon, verberg, verwijderChildElementenVan} from "./util.js";

byId("zoek").onclick = async function () {
    verbergPizzasEnFouten();
    const vanInput = byId("van");
    if (! vanInput.checkValidity()) {
        toon("vanFout");
        vanInput.focus();
        return;
    }
    const totInput = byId("tot");
    if (! totInput.checkValidity()) {
        toon("totFout");
        totInput.focus();
        return;
    }
    findByPrijsBetween(vanInput.value, totInput.value);
}

function verbergPizzasEnFouten() {
    verberg("pizzasTable");
    verberg("vanFout");
    verberg("totFout");
    verberg("storing");
}

async function findByPrijsBetween(van, tot) {
    const response = await fetch(`pizzas?vanPrijs=${van}&totPrijs=${tot}`);
    if (response.ok) {
        const pizzas = await response.json();
        toon("pizzasTable");
        const pizzasBody = byId("pizzasBody");
        verwijderChildElementenVan(pizzasBody);
        for (const pizza of pizzas) {
            const tr = pizzasBody.insertRow();
            tr.insertCell().innerText = pizza.id;
            tr.insertCell().innerText = pizza.naam;
            tr.insertCell().innerText = pizza.prijs;
        }
    } else {
        toon("storing");
    }
}