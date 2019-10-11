package com.lardi.phonebook.controller;

import com.lardi.phonebook.model.Contact;
import com.lardi.phonebook.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class ContactController {

    private ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    /**
     * Request for receiving all contacts of one user
     * @param request
     * @return List<ContactDTO>
     */
    @RequestMapping(value = "/contacts/user",
            method = GET,
            headers = "Accept=application/json; charset=UTF-8",
            produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<?> getContactsUser(final HttpServletRequest request) {
        return ResponseEntity.ok(contactService.getContactsUser(request));
    }

    /**
     * Request to add one contact for one user
     * @param contact
     * @param request
     * @return Message and body with Contact with new Id
     */
    @RequestMapping(value = "/contact/user",
            method = PUT,
            headers = "Accept=application/json; charset=UTF-8",
            produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<?> addContactUser(@Validated @RequestBody final Contact contact,
                                            final HttpServletRequest request) {
        return status(CREATED).body(contactService.addContactUser(request, contact));
    }

    /**
     * Change Request data of contact
     * @param contact
     * @param request
     * @return Message and body with Contact_Change
     */
    @RequestMapping(value = "/contact/user",
            method = POST,
            headers = "Accept=application/json; charset=UTF-8",
            produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<?> editContactUser(@Validated @RequestBody final Contact contact,
                                             final HttpServletRequest request) {
        return status(ACCEPTED).body(contactService.editContactUser(request, contact));
    }

    /**
     * Contact deletion request
     * @param contact
     * @return Message and body with Contact
     */
    @RequestMapping(value = "/contact/user",
            method = DELETE,
            headers = "Accept=application/json; charset=UTF-8",
            produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<?> delContactUser(@RequestBody final Contact contact) {
        return status(ACCEPTED).body(contactService.delContactUser(contact));
    }

     /**
     * For the testing
     * @return All contacts
     */
    @RequestMapping (value = "/test/contacts",
                    method = GET,
            headers = "Accept=application/json; charset=UTF-8",
            produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<?> getContacts() {
        return ResponseEntity.ok(contactService.getAllContacts());

    }
}
