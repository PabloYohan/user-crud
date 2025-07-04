package src.br.dio.dao;

import src.br.dio.Exceptions.EmptyStorageException;
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

    public UserModel update(UserModel user) {
        var foundUser = findModelById(user.getId());
        usersList.remove(foundUser);
        usersList.add(user);
        return user;
    }

    public void delete(long id) {
        var foundUser = findModelById(id);
        usersList.remove(foundUser);
    }

    public UserModel findModelById(long id) {
        String message = String.format("Não existe usuário com id %s", id);
        return usersList.stream().filter(u -> u.getId() == id).findFirst()
                .orElseThrow(() -> new UserNotFoundException(message));
    }

    public List<UserModel> findAll() {
        List<UserModel> result;
        try {
            verifyStorage();
            result = usersList;
        } catch (EmptyStorageException exception) {
            exception.getStackTrace();
            result = new ArrayList<>();
        }
        return result;
    }

    private void verifyStorage() {
        if (usersList.isEmpty()) throw new EmptyStorageException("Não usuários cadastrados.");
    }
}
