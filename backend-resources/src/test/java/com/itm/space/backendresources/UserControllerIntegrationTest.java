package com.itm.space.backendresources;

import com.itm.space.backendresources.api.request.UserRequest;
import com.itm.space.backendresources.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest extends BaseIntegrationTest {


    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(roles = "MODERATOR")
    void testCreateCorrect() throws Exception {

        UserRequest userRequest = new UserRequest("test", "test@gmail.com", "12345", "test", "test");
        mvc.perform(requestWithContent(post("/api/users"), userRequest))
           .andExpect(status().isOk());
        verify(userService, times(1)).createUser(any(UserRequest.class));
    }


    @Test
    @WithMockUser(roles = "MODERATOR")
    void testGetUserById() throws Exception {

        UUID userId = UUID.randomUUID();

        mvc.perform(requestToJson(get("/api/users/{id}", userId)))
           .andExpect(status().isOk());
        verify(userService, times(1)).getUserById(any());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    void testHello() throws Exception {
        mvc.perform(requestToJson(get("/api/users/hello")))
           .andExpect(status().isOk());
    }
}
