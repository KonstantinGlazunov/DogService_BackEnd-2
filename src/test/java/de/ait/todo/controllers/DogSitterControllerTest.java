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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
@DisplayName("Endpoint /dogSitters works:")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
public class DogSitterControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Nested
    @DisplayName("GET /dog-sitters:")
    public class GetDogSitters {

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_empty_list_of_dogSitters_for_empty_database() throws Exception {
            mockMvc.perform(get("/api/dog-sitters"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(0)));
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
                                    "  \"id\": 3,\n" +
                                    "  \"firstName\": \"Marsel\",\n" +
                                    "  \"lastName\": \"Sidikov\",\n" +
                                    "  \"userName\": \"@Leonid\",\n" +
                                    "  \"city\": \"Berlin\",\n" +
                                    "  \"zip\": \"35778\",\n" +
                                    "  \"email\": \"simple@mail.com\",\n" +
                                    "  \"size\": \"A_MINI\",\n" +
                                    "  \"password\": \"Qwerty007!\"\n" +
                                    "}"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(3)));
        }
    }


}
