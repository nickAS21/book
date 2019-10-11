package com.lardi.phonebook.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Message {
    ERROR("Error..."),
    REALM("MY_TEST_REALM"),
    USER_NOT_FOUND_ERROR("User not found!"),
    SECURITY_CONTEXT_ERROR("Could not set user authentication in security context!"),
    INFO_USER_CONTACT_SET("Save contact user body:  "),
    INFO_USER_CONTACTS_GET("All contacts for user body:  "),
    INVALID_GET_USER_LOGIN("No user with login:  "),
    SUCCESS_REGISTRATION("User registered successfully!"),
    SUCCESS_REGISTRATION_CONTACT ("Contact registered successfully! UserId: "),
    SUCCESS_EDIT_CONTACT ("Contact edited successfully! ContactId: "),
    SUCCESS_DELETE_CONTACT ("Contact deleted successfully! ContactId: "),
    SUCCESS_GET_ALL_CONTACT ("Oparation successfully! Total: ");

    private String description;

    public String getDescription () {
        return this.description;
    }


}
