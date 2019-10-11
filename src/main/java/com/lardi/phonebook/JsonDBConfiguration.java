package com.lardi.phonebook;

import com.lardi.phonebook.model.Contact;
import com.lardi.phonebook.model.User;
import com.lardi.phonebook.repository.ContactRepositoryJson;
import com.lardi.phonebook.repository.UserRepositoryJson;
import io.jsondb.JsonDBTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ConditionalOnProperty("json.db.path")
public class JsonDBConfiguration {

    /**
     * jsonDB
     * paeth to DB in current application.properties: json.db.path
     * @param dbPath
     * @return
     */
    @Bean
    public JsonDBTemplate jsonDBTemplate(@Value("${json.db.path}") String dbPath) {
        String baseScanPackage = this.getClass().getPackage().getName();
        JsonDBTemplate jsonDBTemplate = new JsonDBTemplate(dbPath, baseScanPackage);
        if (!jsonDBTemplate.collectionExists(User.class)) jsonDBTemplate.createCollection(User.class);
        if (!jsonDBTemplate.collectionExists(Contact.class)) jsonDBTemplate.createCollection(Contact.class);
        return jsonDBTemplate;
    }

    /**
     * json repositiry for the User.class
     *
     * @param jsonDBTemplate
     * @return
     */
    @Bean(name = "jsonUserRepository")
    @Primary
    public UserRepositoryJson userRepository(JsonDBTemplate jsonDBTemplate) {
        return new UserRepositoryJson(jsonDBTemplate);
    }

    /**
     * json repositiry for the Contact.class
     *
     * @param jsonDBTemplate
     * @return
     */
    @Bean(name = "jsonContactRepository")
    @Primary
    public ContactRepositoryJson contactRepository(JsonDBTemplate jsonDBTemplate) {
        return new ContactRepositoryJson(jsonDBTemplate);
    }
}
