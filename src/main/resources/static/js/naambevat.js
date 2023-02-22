"use strict";
import {byId, toon, verberg, verwijderChildElementenVan} from "./util.js";

byId("zoek").onclick = async function () {
    verbergPizzasEnFouten();
    const woordInput = byId("woord");
    if (woordInput.checkValidity()) {
        await findByWoord(woordInput.value);
    } else {
        toon("woordFout");
        woordInput.focus();
    }
}

function verbergPizzasEnFouten() {
    verberg("pizzasTable");
    verberg("woordFout");
    verberg("storing");
}

async function findByWoord(woord) {
    const response = await fetch(`pizzas?naamBevat=${woord}`);
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