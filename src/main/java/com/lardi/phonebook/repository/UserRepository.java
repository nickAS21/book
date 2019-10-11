package com.lardi.phonebook.repository;

import com.lardi.phonebook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>, IUserRepository {

}
