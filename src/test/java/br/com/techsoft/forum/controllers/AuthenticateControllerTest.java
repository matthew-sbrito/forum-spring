package br.com.techsoft.forum.controllers;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnBadRequestIncorrectLogin() throws Exception {
        URI uri = new URI("/auth");

        JSONObject myJson = new JSONObject();
        myJson.put("email", "invalid@email.com");
        myJson.put("senha", "123456");

        String json = myJson.toString();

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(400));
    }

//    @Test
//    public void shouldReturnOkCorrectLogin() throws Exception {
//        URI uri = new URI("/auth");
//
//        JSONObject myJson = new JSONObject();
//        myJson.put("email", "aluno@email.com");
//        myJson.put("senha", "123456");
//
//        String json = myJson.toString();
//
//        mockMvc
//                .perform(MockMvcRequestBuilders
//                        .post(uri)
//                        .content(json)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers
//                        .status()
//                        .is(200));
//    }
}