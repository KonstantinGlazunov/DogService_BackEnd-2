package de.ait.todo.controllers;

import de.ait.todo.test.config.TestConfig;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
@DisplayName("Endpoint /clinics works:")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
class ClinicsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Nested
    @DisplayName("GET /clinics:")
    public class GetClinics {

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_empty_list_of_clinics_for_empty_database() throws Exception {
            mockMvc.perform(get("/api/clinics"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(0)));
        }

        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_list_of_clinics_for_not_empty_database() throws Exception {
            mockMvc.perform(get("/api/clinics"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(2)))
                    .andExpect(jsonPath("$.[0].id", is(1)))
                    .andExpect(jsonPath("$.[1].id", is(2)))
                    .andExpect(jsonPath("$.[0].name", is("Pets clinic1")));
        }
    }

    @Nested
    @DisplayName("GET /clinics/{clinic-id}:")
    public class GetClinic {

        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_existed_clinic() throws Exception {
            mockMvc.perform(get("/api/clinics/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Pets clinic1")))
                    .andExpect(jsonPath("$.clinicCity", is("Berlin")));
        }


        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_404_for_not_existed_clinic() throws Exception {
            mockMvc.perform(get("/api/clinics/3"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("POST /clinics:")
    public class PostClinic {
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_created_clinic() throws Exception {
            mockMvc.perform(post("/api/clinics")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(" {\n" +
                            "  \"name\": \"Pets clinic\",\n" +
                            "  \"description\": \"Clinic for small and big pets\",\n" +
                            "  \"webSite\": \"https://clinic-example.de\",\n" +
                            "  \"country\": \"Germany\",\n" +
                            "  \"clinicCity\": \"Berlin\",\n" +
                            "  \"postCode\": \"01010\",\n" +
                            "  \"address\": \"Hauptstrasse 7\",\n" +
                            "  \"telephoneNumber\": \"+49 172 111 11 111\"\n" +
                            "} "))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(3)));
        }
    }

    @Nested
    @DisplayName("DELETE /clinics/{clinic-id}:")
    public class DeleteClinic {
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_deleted_clinic() throws Exception {
            mockMvc.perform(delete("/api/clinics/1"))
                    .andExpect(status().isOk());
        }

    }

    @Nested
    @DisplayName("UPDATE /clinics/{clinic-id}:")
    public class UpdateClinic {

        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_updated_clinic() throws Exception {
            mockMvc.perform(put("/api/clinics/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(" {\n" +
                                    "  \"name\": \"Clinic for pets\",\n" +
                                    "  \"description\": \"Clinic for small and big pets\",\n" +
                                    "  \"webSite\": \"https://clinic-example.de\",\n" +
                                    "  \"country\": \"Germany\",\n" +
                                    "  \"clinicCity\": \"Berlin\",\n" +
                                    "  \"postCode\": \"01010\",\n" +
                                    "  \"address\": \"Hauptstrasse 7\",\n" +
                                    "  \"telephoneNumber\": \"+49 172 111 11 111\"\n" +
                                    "} "))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is("Clinic for pets")));
        }

    }


}