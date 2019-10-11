package com.lardi.phonebook.repository;

import com.lardi.phonebook.model.Contact;
import io.jsondb.JsonDBTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ContactRepositoryJson implements IContactRepository {
    private final JsonDBTemplate jsonDBTemplate;


    public ContactRepositoryJson(JsonDBTemplate jsonDBTemplate) {
        this.jsonDBTemplate = jsonDBTemplate;
    }

    /**
     * Find all contacts of user with value==userId
     * @param userId
     * @return List<Contact> user`s
     */
    @Override
    public List<Contact> findAllByUserId(String userId) {
        String jxQuery = String.format("/.[userId='%s']", userId);
        return jsonDBTemplate.find(jxQuery, Contact.class);
    }

    /**
     * Create new contact user`s
     * @param contact
     * @return New contact with new id
     */
    public Contact save(Contact contact) {
        contact.setId(java.util.UUID.randomUUID().toString());
        jsonDBTemplate.insert(contact);
        return contact;
    }

    /**
     * Edit/update contact
     * @param contact
     * @return  Update contact
     */
    public Contact saveAndFlush(Contact contact) {
        jsonDBTemplate.save(contact, Contact.class);
        return contact;
    }

    /**
     * Delete one contact
     * @param contact
     */
    public void delete(Contact contact) {
        jsonDBTemplate.remove(contact, Contact.class);
    }


    /**
     * For the test
     * Find all contact of all uasers
     * @return List<Contact> all users
     */
    public List<Contact> findAll() {
        String jxQuery = "/.[not(privateKey='')]";
        return jsonDBTemplate.find(jxQuery, Contact.class);
    }
}
