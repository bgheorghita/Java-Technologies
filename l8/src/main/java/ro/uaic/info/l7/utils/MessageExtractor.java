package ro.uaic.info.l7.utils;

import javax.ejb.EJBTransactionRolledbackException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

public class MessageExtractor {
    private MessageExtractor() {}

    public static String getMessageFromException(Exception e) {
        String msg = e.getMessage();
        int colonIndex = msg.indexOf(":");
        if (colonIndex != -1) {
            msg = msg.substring(colonIndex + 1).trim();
        }
        return msg;
    }

    public static String extractValidationMessagesFromValidations(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        StringBuilder messages = new StringBuilder("Validation failed. List of constraint violations:\n");

        for (ConstraintViolation<?> violation : violations) {
            String message = String.format("Property: %s, Value: %s, Message: %s\n",
                    violation.getPropertyPath(), violation.getInvalidValue(), violation.getMessage());
            messages.append(message);
        }

        return messages.toString();
    }

    public static String handleValidationException(EJBTransactionRolledbackException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof ConstraintViolationException) {
            ConstraintViolationException validationException = (ConstraintViolationException) cause;
            Set<ConstraintViolation<?>> violations = validationException.getConstraintViolations();
           StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<?> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            return sb.toString();
        } else {
            throw new RuntimeException("Not a ConstraintViolationException");
        }
    }
}
