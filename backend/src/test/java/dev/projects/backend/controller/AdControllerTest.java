package dev.projects.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.projects.backend.collection.Advertisement;
import dev.projects.backend.enums.BusinessCategory;
import dev.projects.backend.enums.PaymentCategory;
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
class AdControllerTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    @DirtiesContext
    @WithMockUser
    void testSaveAd_whenSaveAd_returnAdvertisement__andStatusCode200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ad")
                        .contentType("application/json")
                        .content("""
                             {
                               "advertisementStatus": "NEW",
                               "paymentCategory": "BASIC",
                               "company": {
                                 "name": "Picture Limited",
                                 "address": {
                                 }
                               },
                               "businessCategories": [
                                 "PHOTOGRAPHER"
                               ],
                               "photosID": [
                                 "string"
                               ],
                               "title": "Best picture on earth",
                               "aboutYourself": "I am he/she/it",
                               "detailInformationForService": "This is our Service in Detail",
                               "averagePrice": 100,
                               "priceCategories": [
                                 "PER_HOUR"
                               ],
                               "contacts": [
                                 {
                                   "email": "string",
                                   "phoneNumber": "string",
                                   "homepageURL": "string"
                                 }
                               ],
                               "locations": [
                                 {
                                 }
                               ],
                               "personsID": "123456"
                             }
                                """)
                                .with(csrf()))
                        .andExpect(status().is(200));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/ad"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                        {
                               "advertisementStatus": "NEW",
                               "paymentCategory": "BASIC",
                               "company": {
                                 "name": "Picture Limited",
                                 "address": {
                                 }
                               },
                               "businessCategories": [
                                 "PHOTOGRAPHER"
                               ],
                               "photosID": [
                                 "string"
                               ],
                               "title": "Best picture on earth",
                               "aboutYourself": "I am he/she/it",
                               "detailInformationForService": "This is our Service in Detail",
                               "averagePrice": 100,
                               "priceCategories": [
                                 "PER_HOUR"
                               ],
                               "contacts": [
                                 {
                                   "email": "string",
                                   "phoneNumber": "string",
                                   "homepageURL": "string"
                                 }
                               ],
                               "locations": [
                                 {
                                 }
                               ],
                               "personsID": "123456"
                             }
                             ]
                        """))
                .andExpect(jsonPath("$[0].id").isNotEmpty());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void testGetAllAds_whenGetAllAds_returnEmptyAdsList_andStatusCode200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/ad"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void testGetAdWithId_whenGetAdWithId_returnCorrectAd_andStatusCode200() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/ad")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                               "advertisementStatus": "NEW",
                               "paymentCategory": "BASIC",
                               "company": {
                                 "name": "Picture Limited",
                                 "address": {
                                 }
                               },
                               "businessCategories": [
                                 "PHOTOGRAPHER"
                               ],
                               "photosID": [
                                 "string"
                               ],
                               "title": "Best picture on earth",
                               "aboutYourself": "I am he/she/it",
                               "detailInformationForService": "This is our Service in Detail",
                               "averagePrice": 110,
                               "priceCategories": [
                                 "PER_HOUR"
                               ],
                               "contacts": [
                                 {
                                   "email": "string",
                                   "phoneNumber": "string",
                                   "homepageURL": "string"
                                 }
                               ],
                               "locations": [
                                 {
                                 }
                               ],
                               "personsID": "123456"
                             }
                                """)
                        .with(csrf()))
                .andExpect(status().is(200))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Advertisement ad = objectMapper.readValue(content, Advertisement.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/ad/" + ad.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                               "advertisementStatus": "NEW",
                               "paymentCategory": "BASIC",
                               "company": {
                                 "name": "Picture Limited",
                                 "address": {
                                 }
                               },
                               "businessCategories": [
                                 "PHOTOGRAPHER"
                               ],
                               "photosID": [
                                 "string"
                               ],
                               "title": "Best picture on earth",
                               "aboutYourself": "I am he/she/it",
                               "detailInformationForService": "This is our Service in Detail",
                               "averagePrice": 110,
                               "priceCategories": [
                                 "PER_HOUR"
                               ],
                               "contacts": [
                                 {
                                   "email": "string",
                                   "phoneNumber": "string",
                                   "homepageURL": "string"
                                 }
                               ],
                               "locations": [
                                 {
                                 }
                               ],
                               "personsID": "123456"
                             }
                        """)).andExpect(jsonPath("$.id").value(ad.getId()));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void testGetAdsByBusiness_whenFoundAdByBusiness_returnCorrectAds_andStatusCode200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ad")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                               "advertisementStatus": "NEW",
                               "paymentCategory": "BASIC",
                               "company": {
                                 "name": "Picture Limited",
                                 "address": {
                                 }
                               },
                               "businessCategories": [
                                 "PHOTOGRAPHER"
                               ],
                               "photosID": [
                                 "string"
                               ],
                               "title": "Best picture on earth",
                               "aboutYourself": "I am he/she/it",
                               "detailInformationForService": "This is our Service in Detail",
                               "averagePrice": 120,
                               "priceCategories": [
                                 "PER_HOUR"
                               ],
                               "contacts": [
                                 {
                                   "email": "string",
                                   "phoneNumber": "string",
                                   "homepageURL": "string"
                                 }
                               ],
                               "locations": [
                                 {
                                 }
                               ],
                               "personsID": "123456"
                             }
                                """)
                        .with(csrf()))
                .andExpect(status().is(200));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/ad/business")
                        .param("businessCategory", BusinessCategory.PHOTOGRAPHER.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{
                               "advertisementStatus": "NEW",
                               "paymentCategory": "BASIC",
                               "company": {
                                 "name": "Picture Limited",
                                 "address": {
                                 }
                               },
                               "businessCategories": [
                                 "PHOTOGRAPHER"
                               ],
                               "photosID": [
                                 "string"
                               ],
                               "title": "Best picture on earth",
                               "aboutYourself": "I am he/she/it",
                               "detailInformationForService": "This is our Service in Detail",
                               "averagePrice": 120,
                               "priceCategories": [
                                 "PER_HOUR"
                               ],
                               "contacts": [
                                 {
                                   "email": "string",
                                   "phoneNumber": "string",
                                   "homepageURL": "string"
                                 }
                               ],
                               "locations": [
                                 {
                                 }
                               ],
                               "personsID": "123456"
                             }]
                        """));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void testGetAdsByPaymentCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ad")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                               "advertisementStatus": "NEW",
                               "paymentCategory": "ADVANCE",
                               "company": {
                                 "name": "Picture Limited",
                                 "address": {
                                 }
                               },
                               "businessCategories": [
                                 "PHOTOGRAPHER"
                               ],
                               "photosID": [
                                 "string"
                               ],
                               "title": "Best picture on earth",
                               "aboutYourself": "I am he/she/it",
                               "detailInformationForService": "This is our Service in Detail",
                               "averagePrice": 130,
                               "priceCategories": [
                                 "PER_HOUR"
                               ],
                               "personsID": "123456"
                             }
                                """)
                        .with(csrf()))
                .andExpect(status().is(200));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/ad/payment")
                        .param("paymentCategory", PaymentCategory.ADVANCE.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{
                               "advertisementStatus": "NEW",
                               "paymentCategory": "ADVANCE",
                               "company": {
                                 "name": "Picture Limited",
                                 "address": {
                                 }
                               },
                               "businessCategories": [
                                 "PHOTOGRAPHER"
                               ],
                               "photosID": [
                                 "string"
                               ],
                               "title": "Best picture on earth",
                               "aboutYourself": "I am he/she/it",
                               "detailInformationForService": "This is our Service in Detail",
                               "averagePrice": 130,
                               "priceCategories": [
                                 "PER_HOUR"
                               ],
                               "personsID": "123456"
                             }]
                        """));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void testGetAdsByAveragePriceIsLessOrEqual() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ad")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                               "advertisementStatus": "NEW",
                               "paymentCategory": "ADVANCE",
                               "company": {
                                 "name": "Picture Limited",
                                 "address": {
                                 }
                               },
                               "businessCategories": [
                                 "PHOTOGRAPHER"
                               ],
                               "photosID": [
                                 "string"
                               ],
                               "title": "Best picture on earth",
                               "aboutYourself": "I am he/she/it",
                               "detailInformationForService": "This is our Service in Detail",
                               "averagePrice": 140,
                               "priceCategories": [
                                 "PER_HOUR"
                               ],
                               "personsID": "123456"
                             }
                                """)
                        .with(csrf()))
                .andExpect(status().is(200));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/ad/average-price")
                        .param("averagePrice", "150"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{
                               "advertisementStatus": "NEW",
                               "paymentCategory": "ADVANCE",
                               "company": {
                                 "name": "Picture Limited",
                                 "address": {
                                 }
                               },
                               "businessCategories": [
                                 "PHOTOGRAPHER"
                               ],
                               "photosID": [
                                 "string"
                               ],
                               "title": "Best picture on earth",
                               "aboutYourself": "I am he/she/it",
                               "detailInformationForService": "This is our Service in Detail",
                               "averagePrice": 140,
                               "priceCategories": [
                                 "PER_HOUR"
                               ],
                               "personsID": "123456"
                             }]
                        """));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void testUpdateAd_whenUpdateAd_returnUpdatedAd_andStatusCode200() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/ad")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                               "advertisementStatus": "NEW",
                               "paymentCategory": "BASIC",
                               "company": {
                                 "name": "Picture Limited",
                                 "address": {
                                 }
                               },
                               "businessCategories": [
                                 "PHOTOGRAPHER"
                               ],
                               "photosID": [
                                 "string"
                               ],
                               "title": "Best picture on earth",
                               "aboutYourself": "I am he/she/it",
                               "detailInformationForService": "This is our Service in Detail",
                               "averagePrice": 300,
                               "priceCategories": [
                                 "PER_HOUR"
                               ],
                               "contacts": [
                                 {
                                   "email": "string",
                                   "phoneNumber": "string",
                                   "homepageURL": "string"
                                 }
                               ],
                               "locations": [
                                 {
                                 }
                               ],
                               "personsID": "123456"
                             }
                                """)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Advertisement ad = objectMapper.readValue(content, Advertisement.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/ad/" + ad.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                               "advertisementStatus": "APPROVED",
                               "paymentCategory": "PREMIUM",
                               "company": {
                                 "name": "Picture Limited",
                                 "address": {
                                 }
                               },
                               "businessCategories": [
                                 "HAIR_DRESSER"
                               ],
                               "photosID": [
                                 "string"
                               ],
                               "title": "Best picture on earth",
                               "aboutYourself": "I am the best hair dresser updated",
                               "detailInformationForService": "This is our Service in Detail",
                               "averagePrice": 400,
                               "priceCategories": [
                                 "PER_SERVICE"
                               ],
                               "contacts": [
                                 {
                                   "email": "Hair@dress.com",
                                   "phoneNumber": "+49(0)17613565648",
                                   "homepageURL": "string"
                                 }
                               ],
                               "locations": [
                                 {
                                 }
                               ],
                               "personsID": "987"
                             }
                                """)
                        .with(csrf()))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/ad/" + ad.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                               "advertisementStatus": "APPROVED",
                               "paymentCategory": "PREMIUM",
                               "company": {
                                 "name": "Picture Limited",
                                 "address": {
                                 }
                               },
                               "businessCategories": [
                                 "HAIR_DRESSER"
                               ],
                               "photosID": [
                                 "string"
                               ],
                               "title": "Best picture on earth",
                               "aboutYourself": "I am the best hair dresser updated",
                               "detailInformationForService": "This is our Service in Detail",
                               "averagePrice": 400,
                               "priceCategories": [
                                 "PER_SERVICE"
                               ],
                               "contacts": [
                                 {
                                   "email": "Hair@dress.com",
                                   "phoneNumber": "+49(0)17613565648",
                                   "homepageURL": "string"
                                 }
                               ],
                               "locations": [
                                 {
                                 }
                               ],
                               "personsID": "987"
                             }
                        """));

    }


    @Test
    @DirtiesContext
    @WithMockUser
    void testDeleteAd_whenDeleteAd_returnIsNotFound_andStatusCode200() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/ad")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                               "advertisementStatus": "NEW",
                               "paymentCategory": "BASIC",
                               "company": {
                                 "name": "Picture Limited",
                                 "address": {
                                 }
                               },
                               "businessCategories": [
                                 "PHOTOGRAPHER"
                               ],
                               "photosID": [
                                 "string"
                               ],
                               "title": "Best picture on earth",
                               "aboutYourself": "I am he/she/it",
                               "detailInformationForService": "This is our Service in Detail",
                               "averagePrice": 200,
                               "priceCategories": [
                                 "PER_HOUR"
                               ],
                               "contacts": [
                                 {
                                   "email": "string",
                                   "phoneNumber": "string",
                                   "homepageURL": "string"
                                 }
                               ],
                               "locations": [
                                 {
                                 }
                               ],
                               "personsID": "123456"
                             }
                                """)
                        .with(csrf()))
                .andExpect(status().is(200))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Advertisement ad = objectMapper.readValue(content, Advertisement.class);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/ad/" + ad.getId())
                .with(csrf()))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/ad/" + ad.getId()))
                .andExpect(status().isNotFound());
    }
}