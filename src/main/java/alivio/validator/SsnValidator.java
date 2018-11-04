package alivio.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SsnValidator implements ConstraintValidator<Ssn, String> {

    @Override
    public void initialize(Ssn paramA) {
    }

    @Override
    public boolean isValid(String ssnNo, ConstraintValidatorContext ctx) {
        if (ssnNo == null) {
            return true;
        } else if (ssnNo.matches("\\d{9}")) {
            return true;
        } else if (ssnNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}")) {
            return true;
        } else {
            return false;
        }
    }
}