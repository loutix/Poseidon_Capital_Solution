package org.ocr.poseidon.exceptions;


import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ItemNotFoundException.class)
    public String handleItemNotFoundException(ItemNotFoundException ex, Model model) {
        log.info(" !! Send an Item Not found error message !! ");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }


    @ExceptionHandler(ValidationException.class)
    public String handleValidationException(ValidationException ex, Model model) {
        log.info(" !! Send a Validation error message !! ");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
