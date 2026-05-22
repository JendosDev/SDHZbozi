package com.example.sdhzbozi;

import com.example.sdhzbozi.common.enums.RoleEnum;
import com.example.sdhzbozi.common.model.Role;
import com.example.sdhzbozi.common.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:sdhzbozi;MODE=MySQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=false",
        "cloudinary.url=not-a-cloudinary-url"
})
@AutoConfigureMockMvc
class SdhZboziApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void seedDefaultRole() {
        roleRepository.findByName(RoleEnum.UNDEFINED)
                .orElseGet(() -> roleRepository.save(new Role(RoleEnum.UNDEFINED)));
    }

    @Test
    void contextLoads() {
    }

    @Test
    void registerAndLoginWithValidCredentials() throws Exception {
        String email = "user-" + UUID.randomUUID() + "@example.com";

        mockMvc.perform(post("/api/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstname": "Jan",
                                  "surname": "Novak",
                                  "email": "%s",
                                  "password": "Password1@"
                                }
                                """.formatted(email)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname").value("Jan"))
                .andExpect(jsonPath("$.email").value(email));

        mockMvc.perform(post("/api/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "%s",
                                  "password": "Password1@"
                                }
                                """.formatted(email)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname").value("Jan"))
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    void registerWithoutCsrfIsForbidden() throws Exception {
        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstname": "Jan",
                                  "surname": "Novak",
                                  "email": "csrf-check@example.com",
                                  "password": "Password1@"
                                }
                                """))
                .andExpect(status().isForbidden());
    }
}
