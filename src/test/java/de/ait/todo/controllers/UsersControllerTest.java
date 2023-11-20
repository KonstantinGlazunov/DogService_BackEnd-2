package de.ait.todo.controllers;

import de.ait.todo.models.User;
import de.ait.todo.repositories.*;
import de.ait.todo.test.config.TestConfig;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Endpoint /users works:")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private ClinicsRepository clinicsRepository;

    @MockBean
    private KennelsRepository kennelsRepository;

    @MockBean
    private DogSittersRepository dogSittersRepository;

    @MockBean
    private ConfirmationCodesRepository confirmationCodesRepository;

    @MockBean
    private DogLoverRepository dogLoverRepository;

    @MockBean
    private TasksRepository tasksRepository;


    @Nested
    @DisplayName("POST /registerUser:")
    public class RegisterUser {

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_created_user() throws Exception {
            mockMvc.perform(post("/api/registerUser")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "  \"firstName\": \"User\",\n" +
                                    "  \"lastName\": \"User\",\n" +
                                    "  \"userName\": \"@Leonid\",\n" +
                                    "  \"city\": \"Berlin\",\n" +
                                    "  \"zip\": \"35778\",\n" +
                                    "  \"email\": \"simple@mail.com\",\n" +
                                    "  \"password\": \"Qwerty007!\"\n" +
                                    "}"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.role", is("DOGLOVER")));
        }

        @Test
        public void return_400_for_bad_format_email() throws Exception {
            mockMvc.perform(post("/api/registerUser")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
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

        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_409_for_existed_email() throws Exception {
            mockMvc.perform(post("/api/registerUser")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "  \"email\": \"user@mail.com\",\n" +
                                    "  \"password\": \"Qwerty007!\",\n" +
                                    "  \"userName\": \"User\"\n" +
                                    "}"))
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    @DisplayName("GET /users/my/profile")
    public class GetProfile {

        @Test
        public void return_403_for_unauthorized() throws Exception {
            mockMvc.perform(get("/api/users/my/profile"))
                    .andExpect(status().isUnauthorized());
        }


        @BeforeTestMethod
        void setUp() {
            when(usersRepository.findById(1L)).thenReturn(
                    Optional.of(User.builder()
                            .id(1L)
                            .role(User.Role.USER)
                            .email("user")
                            .userName("User")
                            .state(User.State.CONFIRMED)
                            .build()));
        }
        @WithUserDetails(value = "user")
        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_information_about_current_user() throws Exception {
            mockMvc.perform(get("/api/users/my/profile")).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.email", is("user")))
                    .andExpect(jsonPath("$.role", is("USER")));
        }
    }

    @Nested
    @DisplayName("GET /users:")
    public class GetUsers {

        @WithUserDetails(value = "user")
        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_empty_list_of_users_for_empty_database() throws Exception {
            mockMvc.perform(get("/api/users/allUsers"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(0)));
        }

        @WithUserDetails(value = "user")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_list_of_users_for_not_empty_database() throws Exception {
            mockMvc.perform(get("/api/users/allUsers"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(2)))
                    .andExpect(jsonPath("$.[0].id", is(1)))
                    .andExpect(jsonPath("$.[1].id", is(2)))
                    .andExpect(jsonPath("$.[0].userName", is("User")));
        }
    }
}
