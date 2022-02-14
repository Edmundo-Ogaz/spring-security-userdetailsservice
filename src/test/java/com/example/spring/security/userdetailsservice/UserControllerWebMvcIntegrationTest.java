package com.example.spring.security.userdetailsservice;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerWebMvcIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerRepository customerRepository;
//
//    @MockBean
//    private PasswordEncoder passwordEncoder;

//    @MockBean
//    private CustomUserDetailService userDetailsService;

    @Test
    public void givenAuthRequestOnPublicService_Users_shouldSucceedWith200() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAuthRequestOnPublicService_shouldSucceedWith200() throws Exception {

        System.out.println(" UserControllerWebMvcIntegrationTest customerRepository " + customerRepository);

        CustomerModel customerModel = new CustomerModel(1l, "test", "test", "test@test.cl", "test");
        Mockito.when(customerRepository.findByEmail("test")).thenReturn(customerModel);

        CustomerModel customerModel1 = customerRepository.findByEmail("test");

        JSONObject data = new JSONObject();
        data.put("password", "test");
        data.put("firstName", "test");
        data.put("lastName", "test");
        data.put("email", "test@test.cl");

        mvc.perform(
                MockMvcRequestBuilders.post("/test1")
                        .content(data.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAuthRequestOnPrivateService_Test_shouldSucceedWith200() throws Exception {

        CustomerModel customerModel = new CustomerModel(1l, "rob", "test", "test@test.cl", "$2a$12$m736W0KE7HFXuWnp9m534OOE3VPVgwza19h.hBoLhP.L4Eoc5Nnqa");
        Mockito.when(customerRepository.findByEmail("test@test.cl")).thenReturn(customerModel);

        JSONObject data = new JSONObject();
        data.put("email", "test@test.cl");
        data.put("password", "password");

        mvc.perform(
                MockMvcRequestBuilders.post("/api/services/controller/user/login")
                        .content(data.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
