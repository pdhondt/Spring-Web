"use strict";
import {byId, toon, verberg, setText} from "./util.js";

byId("zoek").onclick = async function () {
    verbergPizzaEnFouten();
    const zoekIdInput = byId("zoekId");
    if (zoekIdInput.checkValidity()) {
        await findById(zoekIdInput.value);
    } else {
        toon("zoekIdFout");
        zoekIdInput.focus();
    }
}

function verbergPizzaEnFouten() {
    verberg("pizza");
    verberg("zoekIdFout");
    verberg("nietGevonden");
    verberg("storing");
    verberg("nieuwePrijsFout");
}

async function findById(id) {
    const response = await fetch(`pizzas/${id}`);
    if (response.ok) {
        const pizza = await response.json();
        toon("pizza");
        setText("naam", pizza.naam);
        setText("prijs", pizza.prijs);
    } else {
        if (response.status === 404) {
            toon("nietGevonden");
        } else {
            toon("storing");
        }
    }
}

byId("bewaar").onclick = async function () {
    const nieuwePrijsInput = byId("nieuwePrijs");
    if (nieuwePrijsInput.checkValidity()) {
        verberg("nieuwePrijsFout");
        await updatePrijs(nieuwePrijsInput.value);
    } else {
        toon("nieuwePrijsFout");
        nieuwePrijsInput.focus();
    }
}

async function updatePrijs(nieuwePrijs) {
    const prijsWijziging = {
        prijs: nieuwePrijs
    };
    const response = await fetch(`pizzas/${byId("zoekId").value}/prijs`,
        {
            method: "PATCH",
            headers: {'Content-Type': "application/json"},
            body: JSON.stringify(prijsWijziging)
        });
    if (response.ok) {
        setText("prijs", nieuwePrijs);
    } else {
        toon("storing");
    }
}

byId("prijzen").onclick = function () {
    const idEnNaam = {id: byId("zoekId").value, naam: byId("naam").innerText};
    sessionStorage.setItem("idEnNaam", JSON.stringify(idEnNaam));
}