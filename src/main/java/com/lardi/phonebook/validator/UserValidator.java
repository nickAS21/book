package com.lardi.phonebook.validator;

import com.lardi.phonebook.model.User;
import com.lardi.phonebook.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class UserValidator implements Validator {

//    @Autowired
    private UserService userService;

    @Autowired
    public UserValidator (UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

         /**
         * Логин (только английские символы, не меньше 3х, без спецсимволов)
         * Проверка на уже зарегистрированых пользователей с таким login
         */
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "NotEmpty");
        if (user.getLogin().length() < 3) {
            errors.rejectValue("login", "Size.userForm.login");
        }
        if (!user.getLogin().matches("[A-Za-z-]+")) {
            errors.rejectValue("login", "Regex.userForm.login"
                    +
                    "are A-Za-z-", "Regex.userForm.login"
                    +
                    "are A-Za-z- .");
        }
        try {
            if (userService.existsUserByLogin(user.getLogin())) {
                errors.rejectValue("login", "Duplicate.userForm.login");
            }
        }
        catch (Exception e) {
                log.error("Error", e);
        }

        /**
         *  ФИО (минимум 5 символов), обязательное для заполнения
         */
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pib", "NotEmpty");
        if (user.getPib().length() < 5) {
            errors.rejectValue("pib", "Size.userForm.pib");
        }

        /**
         * Пароль (минимум 5 символов)
         */
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 5) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        /**
         * Проверка введенного пароля (повторный ввод в поле confirmPassword)
         */
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Diff.userForm.confirmPassword");
        }
    }
}
