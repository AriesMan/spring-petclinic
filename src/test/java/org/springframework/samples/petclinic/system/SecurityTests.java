package org.springframework.samples.petclinic.system;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void loginWithValidUserThenAuthenticated() throws Exception {
        FormLoginRequestBuilder login = formLogin()
            .user("Bill")
            .password("password");

        mockMvc.perform(login)
            .andExpect(authenticated().withUsername("Bill"));
    }

    @Test
    public void loginWithInvalidUserThenUnauthenticated() throws Exception {
        FormLoginRequestBuilder login = formLogin()
            .user("invalid")
            .password("invalidpassword");

        mockMvc.perform(login)
            .andExpect(unauthenticated());
    }

    @Test
    public void accessUnsecuredResourceThenOk() throws Exception {
        mockMvc.perform(get("/login"))
            .andExpect(status().isOk());
    }

    @Test
    public void accessSecuredResourceUnauthenticatedThenRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/owners/new"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "samantha", password = "password", roles = "ADD")
    public void accessSecuredResourceAuthenticatedThenOk() throws Exception {
        FormLoginRequestBuilder login = formLogin()
            .user("Samantha")
            .password("password");
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk());
    }
}
