package com.lardi.phonebook.controller;


import com.lardi.phonebook.model.Contact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.lardi.phonebook.controller.Util.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {


    @Autowired
    private MockMvc mockMvc;

    //get_/contacts/user
    @Test
    @WithMockUser(username = "nickA", password = "123456", roles = "USER")
    public void getContacsUserAuthentificationOk_ReturnContacts_Test() throws Exception {
        assertNotNull(getContactUser());
    }

    @Test
    @WithMockUser(username = "nickC", password = "123456", roles = "USER")
    public void getContacsUserNoPresent_ReturnServerError_Test() throws Exception {
        String responseJson  = mockMvc
                .perform(get("/contacts/user"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Map<String, String> responseMap = mapFromJson(responseJson, Map.class);
        assertTrue(responseMap.get("message").equals("Server Error"));
        Object detailsErr =  responseMap.get("details");
        List detailsErrArr = (ArrayList) detailsErr;
        assertTrue(detailsErrArr.get(0).equals("No value present"));
    }

    // put_/contact/user - add
    @Test
    @WithMockUser(username = "nickA", password = "123456", roles = "USER")
    public void addNewContactUser_ReturnOk_ContactWithUserIdAndNewContactId_Test() throws Exception {
        Contact contact = getContactValidOk(null);
        String responseJson = mockMvc
                .perform(put("/contact/user")
                    .contentType("application/json;charset=UTF-8")
                    .content(requestBody(contact)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Map<String, String> responseMap  = mapFromJson(responseJson, Map.class);
        assertTrue(responseMap.get("message").equals("Contact registered successfully! UserId: nickA"));
        Object body =  responseMap.get("body");
        Map <String, String>  bodyLinkedHashMap = (LinkedHashMap <String, String>) body;
        Contact contactAdd = linkedHashMapToPojo(bodyLinkedHashMap, Contact.class);
        contact.setId(contactAdd.getId());
        contact.setUserId(contactAdd.getUserId());
        assertEquals(contact, contactAdd);
    }

    // post_/contact/user - edit
    @Test
    @WithMockUser(username = "nickA", password = "123456", roles = "USER")
    public void editContactUser_Return_Ok_Test() throws Exception {
        Contact contact = getContactUser()[0];
        contact.setFirstName(contact.getFirstName()+"Edit");
        MvcResult mvcResult = mockMvc
                .perform(post("/contact/user")
                        .contentType("application/json;charset=UTF-8")
                        .content(requestBody(contact)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String responseJson = mvcResult.getResponse().getContentAsString();
        Map<String, String> responseMap  = mapFromJson(responseJson, Map.class);
        Object body =  responseMap.get("body");
        Map <String, String>  bodyLinkedHashMap = (LinkedHashMap <String, String>) body;
        Contact contactEdit = linkedHashMapToPojo(bodyLinkedHashMap, Contact.class);
        assertEquals(contact, contactEdit);
        assertTrue(responseMap.get("message").equals("Contact edited successfully! ContactId: " + contactEdit.getId()));
    }

    // delete_/contact/user
    @Test
    @WithMockUser(username = "nickA", password = "123456", roles = "USER")
    public void deleteContactUser_Return_OkAndSixeCountContactMinusOne_Test() throws Exception {
        Contact [] contacts = getContactUser();
        int size = contacts.length;
        Contact contact = contacts[contacts.length-1];
        MvcResult mvcResult = mockMvc
                .perform(delete("/contact/user")
                        .contentType("application/json;charset=UTF-8")
                        .content(requestBody(contact)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String responseJson = mvcResult.getResponse().getContentAsString();
        Map<String, String> responseMap  = mapFromJson(responseJson, Map.class);
        Object body =  responseMap.get("body");
        Map <String, String>  bodyLinkedHashMap = (LinkedHashMap <String, String>) body;
        Contact contactDelete = linkedHashMapToPojo(bodyLinkedHashMap, Contact.class);
        assertEquals(contact, contactDelete);
        assertTrue(responseMap.get("message").equals("Contact deleted successfully! ContactId: " + contactDelete.getId()));
        int sizeDelete =  getContactUser().length;
        assertEquals((size-1), sizeDelete);
    }

    // get_/test/contacts
    @Test
    public void getTestUsers_Return_AllUsers_Test () throws Exception {
        this.mockMvc.perform(get("/test/contacts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print());
    }

    private Contact [] getContactUser () throws Exception {
        String responseJson = mockMvc
                .perform(get("/contacts/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Contact[] contacts = mapFromJson(responseJson, Contact[].class);
        return (contacts.length > 0) ? contacts : null;
    }
}
