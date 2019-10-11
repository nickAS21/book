package com.lardi.phonebook.repository;

import com.lardi.phonebook.model.Contact;

import java.util.List;

public interface IContactRepository {
    List<Contact> findAllByUserId(String userId);

    Contact saveAndFlush(Contact contact);

    Contact save(Contact contact);

    void delete(Contact contact);

    List<Contact> findAll();
}
