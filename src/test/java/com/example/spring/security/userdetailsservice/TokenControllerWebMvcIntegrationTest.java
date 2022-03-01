package com.example.spring.security.userdetailsservice;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TokenController.class)
public class TokenControllerWebMvcIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void givenAuthRequestOnPublicService_VerifyToken_shouldSucceedWith200() throws Exception {
        CustomerModel customerModel = new CustomerModel(1l, "rob", "test", "test@test.cl", "$2a$12$m736W0KE7HFXuWnp9m534OOE3VPVgwza19h.hBoLhP.L4Eoc5Nnqa");
        Mockito.when(customerRepository.findByUsername("test@test.cl")).thenReturn(customerModel);

        JSONObject dataLogin = new JSONObject();
        dataLogin.put("username", "test@test.cl");
        dataLogin.put("password", "password");

        MvcResult resultLogin = mvc.perform(
                MockMvcRequestBuilders
                        .post("/api/services/controller/user/login")
                        .content(dataLogin.toString())
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();

        String content = resultLogin.getResponse().getContentAsString();
        JSONObject json = new JSONObject(content);
        String token = (String)json.get("token");
        Assert.notNull(token);
        final String URL = "/verifyToken?token=" + token;
        mvc.perform(MockMvcRequestBuilders.get(URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
