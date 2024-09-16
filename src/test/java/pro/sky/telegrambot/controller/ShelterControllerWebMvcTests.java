package pro.sky.telegrambot.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.telegrambot.entity.Shelter;
import pro.sky.telegrambot.interfaces.shelter.ShelterService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShelterController.class)
public class ShelterControllerWebMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShelterService shelterService;

    private final String REQUEST_MAPPING_CONTROLLER = "/shelter";
    private final String SLASH = "/";

    private final Shelter TEST_SHELTER = new Shelter();
    private final Shelter TEST_SHELTER_WITH_NULL_ID = new Shelter();

    private Shelter EXPEPTED_SHELTER = new Shelter();
    private JSONObject TEST_JSON_OBJECT;



    @BeforeEach
    void init_shelter() throws JSONException {
        TEST_SHELTER.setId(1l);
        TEST_SHELTER.setAddress("TEST_address");
        TEST_SHELTER.setName("TEST_name");
        TEST_SHELTER.setWorkSchedule("TES_schedule");
        TEST_SHELTER.setSecurityContact("+7-9***");
        TEST_SHELTER.setGeneralRecommendationsOnSafety("TEST_recommendation");

        TEST_JSON_OBJECT = new JSONObject();
        TEST_JSON_OBJECT.put("id", TEST_SHELTER.getId());
        TEST_JSON_OBJECT.put("address", TEST_SHELTER.getAddress());
        TEST_JSON_OBJECT.put("name", TEST_SHELTER.getName());
        TEST_JSON_OBJECT.put("workSchedule", TEST_SHELTER.getWorkSchedule());
        TEST_JSON_OBJECT.put("securityContact", TEST_SHELTER.getSecurityContact());
        TEST_JSON_OBJECT.put("generalRecommendationsOnSafety", TEST_SHELTER.getGeneralRecommendationsOnSafety());
    }

    @Test
    public void getShelter() throws Exception {
        Mockito.when(shelterService.find(TEST_SHELTER)).thenReturn(TEST_SHELTER);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(REQUEST_MAPPING_CONTROLLER)
                        .content(TEST_JSON_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(TEST_SHELTER.getId()))
                .andExpect(jsonPath("$.address").value(TEST_SHELTER.getAddress()))
                .andExpect(jsonPath("$.name").value(TEST_SHELTER.getName()))
                .andExpect(jsonPath("$.workSchedule").value(TEST_SHELTER.getWorkSchedule()))
                .andExpect(jsonPath("$.securityContact").value(TEST_SHELTER.getSecurityContact()))
                .andExpect(jsonPath("$.generalRecommendationsOnSafety").value(TEST_SHELTER.getGeneralRecommendationsOnSafety()))
        ;
    }

    @Test
    public void addShelter() throws Exception {
        Mockito.when(shelterService.add(TEST_SHELTER)).thenReturn(TEST_SHELTER);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(REQUEST_MAPPING_CONTROLLER)
                        .content(TEST_JSON_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(TEST_SHELTER.getId()))
                .andExpect(jsonPath("$.address").value(TEST_SHELTER.getAddress()))
                .andExpect(jsonPath("$.name").value(TEST_SHELTER.getName()))
                .andExpect(jsonPath("$.workSchedule").value(TEST_SHELTER.getWorkSchedule()))
                .andExpect(jsonPath("$.securityContact").value(TEST_SHELTER.getSecurityContact()))
                .andExpect(jsonPath("$.generalRecommendationsOnSafety").value(TEST_SHELTER.getGeneralRecommendationsOnSafety()))
        ;
    }

    @Test
    public void deleteShelter() throws Exception {
        Mockito.when(shelterService.remove(TEST_SHELTER)).thenReturn(TEST_SHELTER);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(REQUEST_MAPPING_CONTROLLER)
                        .content(TEST_JSON_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(TEST_SHELTER.getId()))
                .andExpect(jsonPath("$.address").value(TEST_SHELTER.getAddress()))
                .andExpect(jsonPath("$.name").value(TEST_SHELTER.getName()))
                .andExpect(jsonPath("$.workSchedule").value(TEST_SHELTER.getWorkSchedule()))
                .andExpect(jsonPath("$.securityContact").value(TEST_SHELTER.getSecurityContact()))
                .andExpect(jsonPath("$.generalRecommendationsOnSafety").value(TEST_SHELTER.getGeneralRecommendationsOnSafety()))
        ;
    }

    @Test
    public void editShelter() throws Exception {
        Mockito.when(shelterService.edit(TEST_SHELTER)).thenReturn(TEST_SHELTER);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(REQUEST_MAPPING_CONTROLLER)
                        .content(TEST_JSON_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(TEST_SHELTER.getId()))
                .andExpect(jsonPath("$.address").value(TEST_SHELTER.getAddress()))
                .andExpect(jsonPath("$.name").value(TEST_SHELTER.getName()))
                .andExpect(jsonPath("$.workSchedule").value(TEST_SHELTER.getWorkSchedule()))
                .andExpect(jsonPath("$.securityContact").value(TEST_SHELTER.getSecurityContact()))
                .andExpect(jsonPath("$.generalRecommendationsOnSafety").value(TEST_SHELTER.getGeneralRecommendationsOnSafety()))
        ;
    }

}
