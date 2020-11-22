package com.beditsch.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Order with such ID does not exist.")
public class OrderNotFoundException extends RuntimeException {
}
