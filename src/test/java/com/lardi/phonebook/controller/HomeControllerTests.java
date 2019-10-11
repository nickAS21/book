package com.lardi.phonebook.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getHomePage1_Test() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .string(getHomePage ()));
    }

    @Test
    public void getHomePage_Test() throws Exception {
        this.mockMvc.perform(get("/home"))
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .string(getHomePage ()));
    }

    @Test
    public void getAboutPage_Test() throws Exception {
        this.mockMvc.perform(get("/about"))
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .string(getAboutPage ()));
    }

    private String getHomePage () {
       return "<!DOCTYPE HTML>\r\n" +
                "<html>\r\n" +
                "<head>\r\n" +
                "    <title>Phonebook Home</title>\r\n" +
                "    <div>\r\n" +
                "            <!-- this is header-css -->\r\n" +
                "            <link rel=\"stylesheet\" href=\"/webjars/bootstrap/3.3.7/css/bootstrap.min.css\"/>\r\n" +
                "            <link rel=\"stylesheet\" href=\"../../css/main.css\"/>\r\n" +
                "<!--            <link rel=\"stylesheet\" href=\"../../css/jquery.alerts.css\" />-->\r\n" +
                "    </div>\r\n" +
                "</head>\r\n" +
                "<body>\r\n" +
                "    <div>\r\n" +
                "    <!-- this is header -->\r\n" +
                "    <nav class=\"navbar navbar-inverse\">\r\n" +
                "        <div class=\"container\">\r\n" +
                "            <div class=\"navbar-header\">\r\n" +
                "                <a class=\"navbar-brand\" href=\"/\">Phonebook</a>\r\n" +
                "            </div>\r\n" +
                "            <div id=\"navbar\" class=\"collapse navbar-collapse\">\r\n" +
                "                <ul class=\"nav navbar-nav\">\r\n" +
                "                    <li class=\"active\"><a href=\"/home\">Home</a></li>\r\n" +
                "                </ul>\r\n" +
                "                <ul class=\"nav navbar-nav\">\r\n" +
                "                    <li class=\"active\"><a href=\"/login\">Login</a></li>\r\n" +
                "                </ul>\r\n" +
                "                <ul class=\"nav navbar-nav\">\r\n" +
                "                    <li class=\"active\"><a href=\"/register\">Registration</a></li>\r\n" +
                "                </ul>\r\n" +
                "                <ul class=\"nav navbar-nav\">\r\n" +
                "                    <li class=\"active\"><a href=\"/about\">About</a></li>\r\n" +
                "                </ul>\r\n" +
                "            </div>\r\n" +
                "        </div>\r\n" +
                "    </nav>\r\n" +
                "</div>\r\n" +
                "    <!-- /.container -->\r\n" +
                "    <div class=\"container\">\r\n" +
                "        <div class=\"starter-template\">\r\n" +
                "            <h1>Spring Boot Web Thymeleaf + Spring Security</h1>\r\n" +
                "            <h2>1. Visit <a href=\"/user\">User page (Spring Security protected, Need User Role)</a></h2>\r\n" +
                "            <h2>2. Visit <a href=\"/register\">Registration page (Spring Security is not protected)</a></h2>\r\n" +
                "            <h2>3. Visit <a href=\"/about\">About page (Spring Security is not protected)</a></h2>\r\n" +
                "        </div>\r\n" +
                "    </div>\r\n" +
                "    <div>\r\n" +
                "\r\n" +
                "    <div class=\"container\">\r\n" +
                "        <footer>\r\n" +
                "            <!-- this is footer -->\r\n" +
                "            &copy; 2019 AS21.com\r\n" +
                "            <span sec:authorize=\"isAuthenticated()\"> |\r\n" +
                "<!--                Logged user: <span sec:authentication=\"name\"></span> |-->\r\n" +
                "<!--                Roles: <span sec:authentication=\"principal.authorities\"></span> |-->\r\n" +
                "                <a href=\"/logout\">Sign Out</a>\r\n" +
                "            </span>\r\n" +
                "        </footer>\r\n" +
                "    </div>\r\n" +
                "</div>\r\n" +
                "</body>\r\n" +
                "</html>\r\n" ;
    }

    private String getAboutPage () {
        return  "<!DOCTYPE HTML>\r\n" +
                "<html>\r\n" +
                "<head>\r\n" +
                "    <title>Phonebook About</title>\r\n" +
                "    <div>\r\n" +
                "            <!-- this is header-css -->\r\n" +
                "            <link rel=\"stylesheet\" href=\"/webjars/bootstrap/3.3.7/css/bootstrap.min.css\"/>\r\n" +
                "            <link rel=\"stylesheet\" href=\"../../css/main.css\"/>\r\n" +
                "<!--            <link rel=\"stylesheet\" href=\"../../css/jquery.alerts.css\" />-->\r\n" +
                "    </div>\r\n" +
                "</head>\r\n" +
                "<body>\r\n" +
                "<div>\r\n" +
                "    <!-- this is header -->\r\n" +
                "    <nav class=\"navbar navbar-inverse\">\r\n" +
                "        <div class=\"container\">\r\n" +
                "            <div class=\"navbar-header\">\r\n" +
                "                <a class=\"navbar-brand\" href=\"/\">Phonebook</a>\r\n" +
                "            </div>\r\n" +
                "            <div id=\"navbar\" class=\"collapse navbar-collapse\">\r\n" +
                "                <ul class=\"nav navbar-nav\">\r\n" +
                "                    <li class=\"active\"><a href=\"/home\">Home</a></li>\r\n" +
                "                </ul>\r\n" +
                "                <ul class=\"nav navbar-nav\">\r\n" +
                "                    <li class=\"active\"><a href=\"/login\">Login</a></li>\r\n" +
                "                </ul>\r\n" +
                "                <ul class=\"nav navbar-nav\">\r\n" +
                "                    <li class=\"active\"><a href=\"/register\">Registration</a></li>\r\n" +
                "                </ul>\r\n" +
                "                <ul class=\"nav navbar-nav\">\r\n" +
                "                    <li class=\"active\"><a href=\"/about\">About</a></li>\r\n" +
                "                </ul>\r\n" +
                "            </div>\r\n" +
                "        </div>\r\n" +
                "    </nav>\r\n" +
                "</div>\r\n" +
                "<!-- /.container -->\r\n" +
                "<div class=\"container\">\r\n" +
                "    <div class=\"starter-template\">\r\n" +
                "        <h1>About</h1>\r\n" +
                "        <div>\r\n" +
                "            <h2>Задание:</h2>\r\n" +
                "                <ol>\r\n" +
                "                    <h3>“Телефонная книга”\r\n" +
                "                        <ol>\r\n" +
                "                            <h4>Требования:</h4>\r\n" +
                "                            <h5>Язык программирования:\r\n" +
                "                                <ul>\r\n" +
                "                                    <li>Java</li>\r\n" +
                "                                </ul>\r\n" +
                "                            </h5>\r\n" +
                "                            <h5>Инструменты:</br>\r\n" +
                "                                <ul>\r\n" +
                "                                    <a href=\"http://java.sun.com\">Справка JDK</a></br>\r\n" +
                "                                <a href=\"http://spring.io/\">Справка Spring</a></br>\r\n" +
                "                                <a href=\"https://maven.apache.org/\">Справка Maven</a>\r\n" +
                "                                </ul>\r\n" +
                "                            </h5>\r\n" +
                "                            <h5>Хранимые данные\r\n" +
                "                                <ol>\r\n" +
                "                                    <h6>\r\n" +
                "                                        <i>Информация о пользователе в системе</i>\r\n" +
                "                                        <ol>\r\n" +
                "                                            <li>Логин (только английские символы, не меньше 3х, без спецсимволов)</li>\r\n" +
                "                                            <li>Пароль (минимум 5 символов)</li>\r\n" +
                "                                            <li> ФИО (минимум 5 символов)</li>\r\n" +
                "                                        </ol>\r\n" +
                "                                    </h6>\r\n" +
                "                                    <h6>\r\n" +
                "                                        <i>Хранимая информация (одна запись)</i>\r\n" +
                "                                        <ol>\r\n" +
                "                                            <li>Фамилия (обязательный, минимум 4 символа)</li>\r\n" +
                "                                            <li>Имя (обязательный, минимум 4 символа)</li>\r\n" +
                "                                            <li>Отчество (обязательный, минимум 4 символа)</li>\r\n" +
                "                                            <li>Телефон мобильный (обязательный)</li>\r\n" +
                "                                            <li>Телефон домашний (не обязательный)</li>\r\n" +
                "                                            <li>Адрес (не обязательный)</li>\r\n" +
                "                                            <li>e-mali (не обязательный, общепринятая валидация)</li>\r\n" +
                "                                        </ol>\r\n" +
                "                                    </h6>\r\n" +
                "                                </ol>\r\n" +
                "                            </h5>\r\n" +
                "                        </ol>\r\n" +
                "                    </h3>\r\n" +
                "                    <h3>Задание:\r\n" +
                "                        <ol>\r\n" +
                "                            <h4>Реализовать Web проект “Телефонная книга”.\r\n" +
                "                                <ol>\r\n" +
                "                                    <h5>Содержащий страницы:\r\n" +
                "                                        <ul>\r\n" +
                "                                            <li>авторизацию</li>\r\n" +
                "                                            <li>Вход в систему (логин/пароль)</li>\r\n" +
                "                                            <li>Регистрация</li>\r\n" +
                "                                            <li>Выход из системы</li>\r\n" +
                "                                        </ul>\r\n" +
                "                                    </h5>\r\n" +
                "                                </ol>\r\n" +
                "                                <ol>\r\n" +
                "                                    <div>\r\n" +
                "                                        <h5> Работа с хранимыми данными\r\n" +
                "                                            <ul>\r\n" +
                "                                                <li>Просмотр всех данных с возможностью фильтрации по имени/фамилии и номеру телефона (не полное соответствие).</li>\r\n" +
                "                                                <li>Добавление/Редактирование/Удаление хранимых записей.</li>\r\n" +
                "                                                <li>Система доступна только авторизованным пользователям.</li>\r\n" +
                "                                                <li>Если пользователь не авторизован, при попытке открытия любой страницы его должно редиректить на страницу авторизации.</li>\r\n" +
                "                                                <li>На странице авторизации он может ввести логин и пароль для входа в систему или зарегистрироваться.</li>\r\n" +
                "                                                <li>При регистрации указываются поля:\r\n" +
                "                                                    <ol>\r\n" +
                "                                                        <i><b>ФИО, логин и пароль.</b></i>\r\n" +
                "                                                    </ol>\r\n" +
                "                                                </li>\r\n" +
                "                                                <li>У каждого авторизованного пользователя имеется своя телефонная книга, т.е. каждый пользователь видит только те записи, которые он создал.</li>\r\n" +
                "                                            </ul>\r\n" +
                "                                        </h5>\r\n" +
                "                                    </div>\r\n" +
                "                                </ol>\r\n" +
                "                                <ol>\r\n" +
                "                                    <h5>Обратить внимание (обязательно к выполнению)\r\n" +
                "                                        <ul>\r\n" +
                "                                            <li>Админка для управления пользователями - не требуется.</li>\r\n" +
                "                                            <li>Формат телефонов должен проверяется и быть валидным для Украины, пример:\r\n" +
                "                                                <ul>\r\n" +
                "                                                    <i><b>+380(66)1234567</b></i>\r\n" +
                "                                                </ul>\r\n" +
                "                                            </li>\r\n" +
                "                                            <li>Приложение обязательно должно содержать JUnit тесты, максимально плотно покрывающие код. Приветствуется использование Mockito.</li>\r\n" +
                "                                            <li>Проект должен собираться средствами <i><b>Maven.</b></i></li>\r\n" +
                "                                            <li>Для запуска использоваться <i><b>SpringBoot.</b></i></li>\r\n" +
                "                                            <li>Исходный код должен быть выложен на <i><b>GitHub</b></i>/BitBucket</li>\r\n" +
                "                                            <li>Все настройки приложения должны находится в properties файле, путь к которому должен передаваться в качестве аргументов JVM машине\r\n" +
                "                                                <ol><i><b>(-Dlardi.conf=/path/to/flie.properties).</b></i></ol>\r\n" +
                "                                            </li>\r\n" +
                "                                            <li>В конфигурационном файле указывается тип хранилища. Тип хранилища используется один раз при старте JVM (изменения в конфигурационном файле вступают в силу только  при перезапуске JVM).</li>\r\n" +
                "                                            <li>Реализовать минимум два варианта хранилища: СУБД (<i><b>MySQL</b></i>) и файл-хранилище (XML/<i><b>JSON</b></i>/CSV на выбор).</li>\r\n" +
                "                                            <li>Настройки хранилища должны указываться в файле-конфигурации (хост и пользователь для СУБД или путь к файлу для файлового хранилища).</li>\r\n" +
                "                                            <li>Для файлового хранилища - в случае отсутствия файла(ов) - его(их) необходимо создать.</li>\r\n" +
                "                                            <li>Для СУБД-хранилища в файле README.md должен находится SQL запрос для создания всех необходимых таблиц.</li>\r\n" +
                "                                            <li>Проверка данных должна осуществляться на стороне сервера.</li>\r\n" +
                "                                            <li>Приложение должно содержать четкое логическое разделение между представление, логикой и источником данных.</li>\r\n" +
                "                                        </ul>\r\n" +
                "                                    </h5>\r\n" +
                "                                </ol>\r\n" +
                "                            </h4>\r\n" +
                "                        </ol>\r\n" +
                "                    </h3>\r\n" +
                "                    <h3>Приветствуется:\r\n" +
                "                        <h5>\r\n" +
                "                        <ul>\r\n" +
                "                            <li>Использование <i><b>JQuery, Bootstrap.</b></i></li>\r\n" +
                "                            <li>Результат оформить в виде докерфайла (собрать проект в <i><b>докер</b></i>)</li>\r\n" +
                "                        </ul></h5>\r\n" +
                "                    </h3>\r\n" +
                "                </ol>\r\n" +
                "        </div>\r\n" +
                "    </div>\r\n" +
                "</div>\r\n" +
                "<div>\r\n" +
                "\r\n" +
                "    <div class=\"container\">\r\n" +
                "        <footer>\r\n" +
                "            <!-- this is footer -->\r\n" +
                "            &copy; 2019 AS21.com\r\n" +
                "            <span sec:authorize=\"isAuthenticated()\"> |\r\n" +
                "<!--                Logged user: <span sec:authentication=\"name\"></span> |-->\r\n" +
                "<!--                Roles: <span sec:authentication=\"principal.authorities\"></span> |-->\r\n" +
                "                <a href=\"/logout\">Sign Out</a>\r\n" +
                "            </span>\r\n" +
                "        </footer>\r\n" +
                "    </div>\r\n" +
                "</div>\r\n" +
                "</body>\r\n" +
                "</html>\r\n";
    }
}
