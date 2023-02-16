"use strict";
import {byId, toon, verberg} from "./util.js";

const response = await fetch("pizzas");
if (response.ok) {
    const pizzas = await response.json();
    const pizzasBody = byId("pizzasBody");
    for (const pizza of pizzas) {
        const tr = pizzasBody.insertRow();
        tr.insertCell().innerText = pizza.id;
        tr.insertCell().innerText = pizza.naam;
        tr.insertCell().innerText = pizza.prijs;
        const td = tr.insertCell();
        const button = document.createElement("button");
        td.appendChild(button);
        button.innerText = "verwijder";
        button.onclick = async function () {
            const response = await fetch(`pizzas/${pizza.id}`, {method: "DELETE"});
            if (response.ok) {
                verberg("storing");
                tr.remove();
            } else {
                toon("storing");
            }
        }
    }
} else {
    toon("storing");
}