package com.sandrocaseiro.apitemplate.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.InputMismatchException;
import java.util.regex.Pattern;

public class CpfValidator implements ConstraintValidator<Cpf, String> {
    private static final Pattern CPF_PATTERN = Pattern.compile("(\\d{3})(\\d{3})(\\d{3})(\\d{2})");

    private static final int CPF_LENGTH = 11;
    private static final int CHAR_OFFSET = 48;
    private static final int DIGIT_1 = 9;
    private static final int DIGIT_2 = 10;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.length() != CPF_LENGTH || !CPF_PATTERN.matcher(s).matches())
            return false;

        try {
            return calculateDigit(s, DIGIT_1, CPF_LENGTH - 1) == s.charAt(DIGIT_1) && calculateDigit(s, DIGIT_2, CPF_LENGTH) == s.charAt(DIGIT_2);
        }
        catch (InputMismatchException ex) {
            return false;
        }
    }

    private static char calculateDigit(String cpf, int pos, int weight) {
        int sm = 0;
        for (int i = 0; i < pos; i++) {
            int num = cpf.charAt(i) - CHAR_OFFSET;
            sm = sm + (num * weight);
            weight = weight - 1;
        }

        int r = CPF_LENGTH - (sm % CPF_LENGTH);
        if (r == CPF_LENGTH - 1 || r == CPF_LENGTH)
            return '0';
        else
            return (char) (r + CHAR_OFFSET);
    }
}
