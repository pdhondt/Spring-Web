package be.vdab.luigi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PizzaBestaatAlException extends RuntimeException {
    public PizzaBestaatAlException(String naam) {
        super("Een pizza bestaat al met volgende naam: " + naam);
    }
}
