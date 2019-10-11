package com.lardi.phonebook.repository;

import com.lardi.phonebook.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    User save(User user);

    Optional<User> findByLogin(String login);

    Boolean existsUserByLogin(String login);

    List<User> findAll();
}
