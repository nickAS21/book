package com.lardi.phonebook.service;

import com.lardi.phonebook.dto.GenericResponseDTO;
import com.lardi.phonebook.message.Message;
import com.lardi.phonebook.model.Contact;
import com.lardi.phonebook.model.User;
import com.lardi.phonebook.repository.IContactRepository;
import com.lardi.phonebook.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
public class ContactService {

    private IContactRepository contactRepository;
    private IUserRepository userRepository;

    @Autowired
    public ContactService(final IContactRepository contactRepository,
                          final IUserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }


    /**
     * All contacts of one user`s
     * @param request
     * @return List <Contact> all contacts of the one user`s
     */
    public List<Contact> getContactsUser(HttpServletRequest request) {
        final String userId = userRepository.findByLogin(request.getUserPrincipal().getName()).get().getId();
        return contactRepository.findAllByUserId(userId);
    }

    /**
     * Insert one contact for one user`s
     * @param request
     * @param contact
     * @return GenericResponseDTO with Message and body with Contact_insert with new id
     */
    public GenericResponseDTO<?> addContactUser(HttpServletRequest request, Contact contact) {
        User user = userRepository.findByLogin(request.getUserPrincipal().getName()).get();
        log.debug(Message.INFO_USER_CONTACT_SET.getDescription() + user.getLogin());
        contact.setUserId(user.getId());
        Contact contactAdd = contactRepository.save(contact);
        GenericResponseDTO<Contact> responseDTO = new GenericResponseDTO<>();
        responseDTO.setMessage(Message.SUCCESS_REGISTRATION_CONTACT.getDescription() + user.getLogin());
        responseDTO.setBody(contactAdd);
        return responseDTO;
    }

    /**
     * Update data data of contact
     * @param request
     * @param contact
     * @return GenericResponseDTO with Message and body with Contact_Update
     */
    public GenericResponseDTO<?> editContactUser( HttpServletRequest request, Contact contact) {
        User user = userRepository.findByLogin(request.getUserPrincipal().getName()).get();
        contact.setUserId(user.getId());
        contactRepository.saveAndFlush (contact);
        GenericResponseDTO<Contact> responseDTO = new GenericResponseDTO<>();
        responseDTO.setMessage(Message.SUCCESS_EDIT_CONTACT.getDescription() + contact.getId());
        responseDTO.setBody(contact);
        return responseDTO;
    }

    /**
     * Delete one contact of user`s
     * @param contact
     * @return GenericResponseDTO with Message and body with Contact_Delete
     */
    public GenericResponseDTO<?> delContactUser( Contact contact) {
        contactRepository.delete(contact);
        GenericResponseDTO<Contact> responseDTO = new GenericResponseDTO<>();
        responseDTO.setMessage(Message.SUCCESS_DELETE_CONTACT.getDescription() + contact.getId());
        responseDTO.setBody(contact);
        return responseDTO;
    }

    /**
     * All contacts for the all users`s
     * For the testing
     * @return List <Contact> all contacts of the all users
     */
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
}
