package com.beditsch.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Cannot assign restaurant.")
public class RestaurantCannotBeAsignedToUserException extends RuntimeException {

}
