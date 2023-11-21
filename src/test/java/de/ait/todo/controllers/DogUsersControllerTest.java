package de.ait.todo.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
@DisplayName("Endpoint /users works:")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
public class DogUsersControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Nested
    @DisplayName("GET /dog-sitters:")
    public class GetDogSitters {

        @Test
        public void return_404_for_empty_list_of_sitters() throws Exception {
            mockMvc.perform(get("/api/dog-sitters"))
                    .andExpect(status().isNotFound());
        }

        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_list_of_dogSitters_for_not_empty_database() throws Exception {
            mockMvc.perform(get("/api/dog-sitters"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(2)))
                    .andExpect(jsonPath("$.[0].id", is(1)))
                    .andExpect(jsonPath("$.[1].id", is(2)))
                    .andExpect(jsonPath("$.[0].email", is("simple@mail.com")));
        }
    }


    @Nested
    @DisplayName("POST /registerSetter:")
    public class PostDogSetter {
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_created_dogSitter() throws Exception {
            mockMvc.perform(post("/api/registerSetter")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(" {\n" +
                                    "  \"id\": 4,\n" +
                                    "  \"firstName\": \"Marsel\",\n" +
                                    "  \"lastName\": \"Sidikov\",\n" +
                                    "  \"userName\": \"@Leonid\",\n" +
                                    "  \"city\": \"Berlin\",\n" +
                                    "  \"zip\": \"35778\",\n" +
                                    "  \"email\": \"sitter@mail.com\",\n" +
                                    "  \"size\": \"A_MINI\",\n" +
                                    "  \"password\": \"Qwerty007!\"\n" +
                                    "}"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(4)));
        }

        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_400_for_bad_format_email() throws Exception {
            mockMvc.perform(post("/api/registerSetter")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(" {\n" +
                                    "  \"id\": 4,\n" +
                                    "  \"firstName\": \"Marsel\",\n" +
                                    "  \"lastName\": \"Sidikov\",\n" +
                                    "  \"userName\": \"@Leonid\",\n" +
                                    "  \"city\": \"Berlin\",\n" +
                                    "  \"zip\": \"35778\",\n" +
                                    "  \"email\": \"sitter.mail.com\",\n" +
                                    "  \"size\": \"A_MINI\",\n" +
                                    "  \"password\": \"Qwerty007!\"\n" +
                                    "}"))
                    .andExpect(status().isBadRequest());
        }

    }

    @Nested
    @DisplayName("POST /registerUser:")
    public class RegisterUser {

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_created_user() throws Exception {
            mockMvc.perform(post("/api/registerUser")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(" {\n" +
                                    "  \"id\": 2,\n" +
                                    "  \"firstName\": \"Marsel\",\n" +
                                    "  \"lastName\": \"Sidikov\",\n" +
                                    "  \"userName\": \"@Leonid\",\n" +
                                    "  \"city\": \"Berlin\",\n" +
                                    "  \"zip\": \"35778\",\n" +
                                    "  \"email\": \"lover@mail.com\",\n" +
                                    "  \"password\": \"Qwerty007!\"\n" +
                                    "}"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(2)));
        }


        @Test
        public void return_400_for_bad_format_email() throws Exception {
            mockMvc.perform(post("/api/registerUser")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "  \"id\": 2,\n" +
                                    "  \"firstName\": \"User\",\n" +
                                    "  \"lastName\": \"User\",\n" +
                                    "  \"userName\": \"@Leonid\",\n" +
                                    "  \"city\": \"Berlin\",\n" +
                                    "  \"zip\": \"35778\",\n" +
                                    "  \"email\": \"usermail.mail.com\",\n" +
                                    "  \"password\": \"Qwerty007!\"\n" +
                                    "}"))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("GET /users:")
    public class GetUsers {

        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_list_of_users_for_not_empty_database() throws Exception {
            mockMvc.perform(get("/api/users/allUsers"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(3)))
                    .andExpect(jsonPath("$.[0].id", is(1)))
                    .andExpect(jsonPath("$.[1].id", is(2)))
                    .andExpect(jsonPath("$.[2].userName", is("Max")));
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_empty_list_of_users_for_database_without_users() throws Exception {
            mockMvc.perform(get("/api/users/allUsers"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(1)));
        }
    }


}
