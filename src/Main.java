package src;

import src.br.dio.Exceptions.EmptyStorageException;
import src.br.dio.Exceptions.UserNotFoundException;
import src.br.dio.dao.UserDAO;
import src.br.dio.model.MenuOption;
import src.br.dio.model.UserModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final UserDAO dao = new UserDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Bem vindo ao cadastro do usuários, selecione a operação desejada");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Excluir");
            System.out.println("4 - Buscar por Id");
            System.out.println("5 - Listar usuários");
            System.out.println("6 - Sair");
            int userInput = scanner.nextInt();
            MenuOption selectedOption = MenuOption.values()[userInput - 1];

            switch (selectedOption) {
                case SAVE -> {
                    UserModel user = UserToSave();
                    dao.save(user);
                }
                case UPDATE -> {
                    try {
                        UserModel user = UserToUpdate();
                        dao.update(user);
                    } catch (UserNotFoundException exception) {
                        System.out.println(exception.getMessage());
                    }
                }
                case DELETE -> {
                    try {
                        dao.delete(requestId());
                    } catch (UserNotFoundException exception) {
                        System.out.println(exception.getMessage());
                    }
                }
                case FIND_BY_ID -> {
                    try {
                        UserModel user = dao.findModelById(requestId());
                        System.out.printf("Usuário: %s, email: %s, Nacimento: %s \n", user.getName(),
                                user.getEmail(), user.getBirthday());
                    } catch (UserNotFoundException exception) {
                        System.out.println(exception.getMessage());
                    }
                }
                case FIND_ALL -> {
                    try {
                        List<UserModel> users = dao.findAll();
                        users.forEach(u -> System.out
                                .printf("Usuário: %s, email: %s, Nacimento: %s \n",
                                        u.getName(),
                                        u.getEmail(), u.getBirthday()));
                    } catch (EmptyStorageException ex) {
                        System.out.println(ex.getMessage());
                    }

                }
                case EXIT -> {
                    System.exit(0);
                }
            }
        }
    }

    private static long requestId() {
        return scanner.nextLong();
    }

    private static UserModel UserToSave() {
        System.out.println("Informe o nome do Usuário");
        String name = scanner.next();
        System.out.println("Informe o email do Usuário");
        String email = scanner.next();
        System.out.println("Informe a data de nascimento do Usuário");
        String birthdayString = scanner.next();

        DateTimeFormatter formated = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthday = LocalDate.parse(birthdayString, formated);

        return new UserModel(0, name, email, birthday);
    }

    private static UserModel UserToUpdate() {
        System.out.println("Informe o id do Usuário");
        long id = scanner.nextLong();
        System.out.println("Informe o nome do Usuário");
        String name = scanner.next();
        System.out.println("Informe o nome do Usuário");
        String email = scanner.next();
        System.out.println("Informe o nome do Usuário");
        String birthdayString = scanner.next();

        DateTimeFormatter formated = DateTimeFormatter.ofPattern("dd/MM/yy");
        LocalDate birthday = LocalDate.parse(birthdayString, formated);

        return new UserModel(id, name, email, birthday);
    }
}
