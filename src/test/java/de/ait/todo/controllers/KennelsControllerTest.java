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
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
@DisplayName("Endpoint /kennels works:")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
class KennelsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("GET /kennels:")
    public class GetKennels {
        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_empty_list_of_kennels_for_empty_database() throws Exception {
            mockMvc.perform(get("/api/kennels"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(0)));
        }

        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_list_of_kennels_for_not_empty_database() throws Exception {
            mockMvc.perform(get("/api/kennels"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(2)))
                    .andExpect(jsonPath("$.[0].id", is(1)))
                    .andExpect(jsonPath("$.[1].id", is(2)))
                    .andExpect(jsonPath("$.[0].name", is("Pets kennel1")));
        }

    }

    @Nested
    @DisplayName("GET /kennels/{kennel-id}:")
    public class GetKennel {

        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_existed_kennel() throws Exception {
            mockMvc.perform(get("/api/kennels/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Pets kennel1")))
                    .andExpect(jsonPath("$.kennelCity", is("Berlin")));
        }


        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_404_for_not_existed_kennel() throws Exception {
            mockMvc.perform(get("/api/kennels/3"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("POST /kennels:")
    public class PostKennel {
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_created_kennel() throws Exception {
            mockMvc.perform(post("/api/kennels")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(" {\n" +
                                    "  \"name\": \"Dogs kennel\",\n" +
                                    "  \"description\": \"Kennel for small and big dogs\",\n" +
                                    "  \"webSite\": \"https://kennel-example.de\",\n" +
                                    "  \"country\": \"Germany\",\n" +
                                    "  \"kennelCity\": \"Berlin\",\n" +
                                    "  \"postCode\": \"01611\",\n" +
                                    "  \"address\": \"Pragerstrasse 7\",\n" +
                                    "  \"telephoneNumber\": \"+4917211777111\"\n" +
                                    "} "))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(3)));
        }
    }

    @Nested
    @DisplayName("DELETE /kennels/{kennel-id}:")
    public class DeleteKennel {
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_deleted_kennel() throws Exception {
            mockMvc.perform(delete("/api/kennels/1"))
                    .andExpect(status().isOk());
        }

    }

    @Nested
    @DisplayName("UPDATE /kennels/{kennel-id}:")
    public class UpdateKennel {

        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_updated_kennel() throws Exception {
            mockMvc.perform(put("/api/kennels/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(" {\n" +
                                    "  \"name\": \"Kennel for dogs\",\n" +
                                    "  \"description\": \"Kennel for small and big dogs\",\n" +
                                    "  \"webSite\": \"https://kennel-example.de\",\n" +
                                    "  \"country\": \"Germany\",\n" +
                                    "  \"kennelCity\": \"Berlin\",\n" +
                                    "  \"postCode\": \"01611\",\n" +
                                    "  \"address\": \"Pragerstrasse 7\",\n" +
                                    "  \"telephoneNumber\": \"+4917211777111\"\n" +
                                    "} "))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is("Kennel for dogs")));
        }
    }
}