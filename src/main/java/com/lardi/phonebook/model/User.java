package com.lardi.phonebook.model;

import io.jsondb.annotation.Document;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Set;


@Entity
@Data
@Document(collection = "users", schemaVersion= "1.0")
@Table(name = "user")
public class User {
    /**
     * PostgreSQL you should use @Type(type = "pg-uuid") instead.
     * @Column(length = 36) is important to reduce from 255 to 36 the field length in MySQL.
     */
    @io.jsondb.annotation.Id
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "pib")
    private String pib;

    @Transient
    @Column(name = "confirm_password")
    private String confirmPassword;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Set<Contact> contacts;

}
