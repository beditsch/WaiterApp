package com.beditsch.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Food category with such ID does not exist in restaurant's menu.")
public class FoodCategoryNotFoundException extends RuntimeException {
}
