package br.com.caelum.carangobom.brand;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Brand not found!" )
public class NotFoundBrandException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotFoundBrandException() {
		super();
	}
	
}
