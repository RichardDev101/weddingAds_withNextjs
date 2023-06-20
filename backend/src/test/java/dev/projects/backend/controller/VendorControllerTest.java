package dev.projects.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.projects.backend.collection.Person;
import dev.projects.backend.collection.Vendor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VendorControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Test
    @DirtiesContext
    @WithMockUser
    void testSaveVendor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/vendor")
                        .contentType("application/json")
                        .content("""
                             {
                                "userName": "TestEditor",
                                "passWord": "123",
                                "firstName": "Test",
                                "lastName": "Mueller"
                              }
                                """)
                        .with(csrf()))
                .andExpect(status().is(200));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/vendors"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                        {
                                "userName": "TestEditor",
                                "passWord": "123",
                                "firstName": "Test",
                                "lastName": "Mueller",
                                "loginRole": "VENDOR"
                              }
                             ]
                        """))
                .andExpect(jsonPath("$[0].personId").isNotEmpty());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void testGetAllVendors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vendors"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void testGetVendorById() throws Exception {
        MvcResult result= mockMvc.perform(MockMvcRequestBuilders.post("/api/vendor")
                        .contentType("application/json")
                        .content("""
                             {
                                "userName": "TestEditor",
                                "passWord": "123",
                                "firstName": "Test",
                                "lastName": "Mueller"
                              }
                                """)
                        .with(csrf()))
                .andExpect(status().is(200))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Person addedVendor = objectMapper.readValue(content, Vendor.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/vendor/"+addedVendor.getPersonId()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                                "userName": "TestEditor",
                                "passWord": "123",
                                "firstName": "Test",
                                "lastName": "Mueller",
                                "loginRole": "VENDOR"
                              }
                        """))
                .andExpect(jsonPath("$.personId").value(addedVendor.getPersonId()));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void update() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/vendor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "userName": "TestAdmin",
                                "passWord": "123",
                                "firstName": "Test",
                                "lastName": "VENDOR"
                              }
                                """)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Vendor addedVendor = objectMapper.readValue(content, Vendor.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/vendor/" + addedVendor.getPersonId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "userName": "NEW_Vendor",
                                "passWord": "987",
                                "firstName": "Hans Dieter",
                                "lastName": "Genscher",
                                "loginRole": "VENDOR"
                              }
                                """)
                        .with(csrf()))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/vendor/" + addedVendor.getPersonId()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                                "userName": "NEW_Vendor",
                                "passWord": "987",
                                "firstName": "Hans Dieter",
                                "lastName": "Genscher",
                                "loginRole": "VENDOR"
                              }
                        """));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void deleteVendor() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/vendor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "userName": "TestEditor",
                                "passWord": "987",
                                "firstName": "Hans Dieter",
                                "lastName": "Genscher"
                              }
                                """)
                        .with(csrf()))
                .andExpect(status().is(200))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Vendor addedVendor = objectMapper.readValue(content, Vendor.class);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/vendor/" + addedVendor.getPersonId())
                        .with(csrf()))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/vendor/" + addedVendor.getPersonId()))
                .andExpect(status().isNotFound());
    }
}