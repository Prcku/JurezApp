package sk.server.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import sk.server.backend.controller.RezervationController;
import sk.server.backend.controller.UserController;
import sk.server.backend.domain.User;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class UserTests {


    @LocalServerPort
    private int port;

    @Autowired
    private UserController userController;

    @Autowired
    private RezervationController rezervationController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getAllUsers(){
        Assertions.assertThat(userController).isNotNull();
    }

    @Test
    void getResultByUserId() throws Exception{
        MvcResult result = mockMvc.perform(get("/user/10"))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        User user = new ObjectMapper().readValue(json, User.class);
        System.out.println(user.toString());
    }
}
