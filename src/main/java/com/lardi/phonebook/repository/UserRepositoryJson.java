package com.lardi.phonebook.repository;

import com.lardi.phonebook.model.User;
import io.jsondb.JsonDBTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class UserRepositoryJson implements IUserRepository {
    private final JsonDBTemplate jsonDBTemplate;


    public UserRepositoryJson(JsonDBTemplate jsonDBTemplate) {
        this.jsonDBTemplate = jsonDBTemplate;
    }

    /**
     * Find one user with value==id
     * @param id
     * @return User
     */
    public User findById(String id) {
        return jsonDBTemplate.findById(id, User.class);
    }

    /**
     * Create new user
     * @param user
     * @return User with new id
     */
    public User save(User user) {
        user.setId(java.util.UUID.randomUUID().toString());
        jsonDBTemplate.insert(user);
        return user;
    }

    /**
     * Find of user with value==login
     * @param login
     * @return User (if there is a match user.login == login)
     * @return Optional.empty() (If there is no match user.login == login)
     */
    public Optional<User> findByLogin(String login) {
        String jxQuery = String.format("/.[login='%s']", login);
        List<User> users = jsonDBTemplate.find(jxQuery, User.class);
        if (users.size() > 0) {
            return Optional.of(users.get(0));
        }
        return Optional.empty();
    }

    /**
     * Find of user with value==login
     * @param login
     * @return false (not Present)
     * @return true (isPresent)
     */
    public Boolean existsUserByLogin(String login) {
        Optional<User> userOpt = findByLogin(login);
        return userOpt.isPresent();
    }

    /**
     * Find all users for the test
     * @return List<User> all users
     */
    @Override
    public List<User> findAll() {
        String jxQuery = "/.[not(privateKey='')]";
        return jsonDBTemplate.find(jxQuery, User.class);
    }

}
