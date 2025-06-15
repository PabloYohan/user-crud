package src.br.dio.dao;

import src.br.dio.Exceptions.UserNotFoundException;
import src.br.dio.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private long nextId = 1L;

    private List<UserModel> usersList = new ArrayList<>();

    public UserModel save(UserModel user) {
        user.setId(nextId++);
        usersList.add(user);
        return user;
    }

    public UserModel findModelById(long id) {
        String message = String.format("Não existe usuário com id %s", id);
        return usersList.stream().filter(u -> u.getId() == id).findFirst()
                .orElseThrow(() -> new UserNotFoundException(message));
    }
}
