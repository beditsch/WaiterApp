package com.beditsch.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "The food category has some meals assigned to it.")
public class FoodCategoryHasMealsAssignedException extends RuntimeException {
}
