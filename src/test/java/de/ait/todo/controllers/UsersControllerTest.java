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

@SpringBootTest
        (classes = TestConfig.class)
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
    @DisplayName("GET /users/my/profile")
    public class GetProfile {

        @Test
        public void return_403_for_unauthorized() throws Exception {
            mockMvc.perform(get("/api/users/my/profile"))
                    .andExpect(status().isUnauthorized());
        }


        @WithUserDetails(value = "user")
        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @BeforeTestMethod
        void return_information_about_user() {
            when(usersRepository.findById(1L)).thenReturn(
                    Optional.of(User.builder()
                            .id(1L)
                            .role(User.Role.DOGLOVER)
                            .email("user")
                            .userName("User")
                            .state(User.State.CONFIRMED)
                            .build()));
        }
        public void return_information_about_current_user() throws Exception {
            mockMvc.perform(get("/api/users/my/profile")).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.email", is("user@mail.com")))
                    .andExpect(jsonPath("$.role", is("DOGLOVER")));
        }

        @WithUserDetails(value = "admin")
        @Test
        @BeforeTestMethod
        void return_information_about_admin() {
            when(usersRepository.findById(1L)).thenReturn(
                    Optional.of(User.builder()
                            .id(1L)
                            .role(User.Role.ADMIN)
                            .email("admin")
                            .userName("Admin")
                            .state(User.State.CONFIRMED)
                            .build()));
        }
        public void getProfile_for_Admin() throws Exception {
            mockMvc.perform(get("/api/users/my/profile")).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.email", is("admin")))
                    .andExpect(jsonPath("$.role", is("ADMIN")));
        }
    }


}
