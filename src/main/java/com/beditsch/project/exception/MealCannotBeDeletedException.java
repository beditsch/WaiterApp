package com.beditsch.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Meal with such ID cannot be deleted.")
public class MealCannotBeDeletedException extends RuntimeException {
}
