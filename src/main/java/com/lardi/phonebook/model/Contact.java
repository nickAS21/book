package com.lardi.phonebook.model;

import com.lardi.phonebook.annotation.PatternNumberCastom;
import io.jsondb.annotation.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *   Хранимая информация (одна запись)
 *   Фамилия (обязательный, минимум 4 символа)
 *   Имя (обязательный, минимум 4 символа)
 *   Отчество (обязательный, минимум 4 символа)
 *   Телефон мобильный (обязательный)
 *   Телефон домашний (не обязательный)
 *   Формат телефонов должен проверяется и быть валидным для Украины, пример:
 *   +380(66)1234567
 *   Адрес (не обязательный)
 *   e-mail (не обязательный, общепринятая валидация)
 */

@EqualsAndHashCode
@Data
@Entity
@Document(collection = "contacts", schemaVersion= "1.0")
@Table(name = "contact")
public class Contact {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
    @io.jsondb.annotation.Id
    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

    //    Фамилия
    @NotBlank(message = "Field: Contact.middleName. Value: Not be blank....")
    @Size(min = 4, message="Field: Contact.firstName. Value: Invalid size. Size must be 4 and  more....")
    @Column(name = "first_name")
    private String firstName;

    //    Имя
    @NotNull
    @NotBlank(message = "Field: Contact.middleName. Value: Not be blank....")
    @Size(min = 4, message = "Field: Contact.lastName. Value: Invalid size. Size must be 4 and  more....")
    @Column(name = "last_name")
    private String lastName;

    //    Отчество
    @NotBlank(message = "Field: Contact.middleName. Value: Not be blank....")
    @Size(min = 4, message="Field: Contact.middleName. Value: Invalid size. Size must be 4 and  more....")
    @Column(name = "middle_name")
    private String middleName;

    //    Телефон мобильный
    /**
     * +380(66)1234567 => ^\\+?3?8?(0\\([5-9][0-9]\\)\\d{7})$
     * value null or empty - is not possible
     */
    @NotBlank(message = "Field: Contact.mobilePhone. Value: Not be blank....")
    @Pattern(regexp = "^\\+?3?8?(0\\([5-9][0-9]\\)\\d{7})$", message="Field: Contact.mobilePhone. Value: Invalid mobilePhone format (Telephone numbers in Ukraine): example +380(66)1234567")
    @Column(name = "mobile_phone")
    private String mobilePhone;

    //    Телефон домашний
    /**
     *  +380(44)3412222 => ^\\+?3?8?(0[3-6][1-8]\\d{7})$
     *   value null or empty - possible
     */
   @PatternNumberCastom(regexp = "^\\+?3?8?(0\\([3-6][1-8]\\)\\d{7})$",  message="Field: Contact.homePhone. Value: Invalid homePhone format (Telephone numbers in Ukraine): example +380(44)1234567")
   @Column(name = "home_phone")
   private String homePhone;

    //    Адрес
    @Column(name = "address")
    private String address;

    //    e-mail
    @Column(name = "email")
    @Email(message="Field: Contact.email. Value: Please provide a valid email address")
    private String email;

    @Column(name="user_id", nullable=false)
    private String userId;

}
