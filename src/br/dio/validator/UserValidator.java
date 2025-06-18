package src.br.dio.validator;

import src.br.dio.Exceptions.ValidatorException;
import src.br.dio.model.UserModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private static Pattern pattern = Pattern
            .compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static Matcher matcher;

    private UserValidator() {

    }

    public static void verifyModel(final UserModel user) {
        if (user.getName() == null || user.getName().isEmpty()) throw new ValidatorException("O nome não " +
                "pode ser nulo");
        matcher = pattern.matcher(user.getEmail());
        if (!matcher.matches()) {
            throw new ValidatorException("O email não é valido");
        }
    }
}
