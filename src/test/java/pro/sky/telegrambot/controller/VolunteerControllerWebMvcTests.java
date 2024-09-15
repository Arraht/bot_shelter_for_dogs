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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.telegrambot.entity.Volunteer;
import pro.sky.telegrambot.interfaces.volunteer.VolunteerService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VolunteerController.class)
public class VolunteerControllerWebMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolunteerService volunteerService;

    private final String REQUEST_MAPPING_CONTROLLER = "/volunteer";
    private final String SLASH = "/";

    private final Volunteer TEST_VOLUNTEER = new Volunteer();
    private final Volunteer TEST_VOLUNTEER_WITH_NULL_ID = new Volunteer();

    private Volunteer EXPEPTED_VOLUNTEER = new Volunteer();
    private JSONObject TEST_JSON_OBJECT;

    private LocalDateTime TEST_LOCAL_DATE_TIME = LocalDateTime.now();

//    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnnn");
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
//    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");


    @BeforeEach
    void init_volunteer() throws JSONException {
        TEST_VOLUNTEER.setId(1l);
        TEST_VOLUNTEER.setName("TEST_name");
        TEST_VOLUNTEER.setNickName("TEST_NICK");
        TEST_VOLUNTEER.setWorkingFirstTime(TEST_LOCAL_DATE_TIME);
        TEST_VOLUNTEER.setWorkingLastTime(TEST_LOCAL_DATE_TIME);

        TEST_JSON_OBJECT = new JSONObject();
        TEST_JSON_OBJECT.put("id", TEST_VOLUNTEER.getId());
        TEST_JSON_OBJECT.put("name", TEST_VOLUNTEER.getName());
        TEST_JSON_OBJECT.put("workingFirstTime", formatter.format(TEST_VOLUNTEER.getWorkingFirstTime() ) );
        TEST_JSON_OBJECT.put("workingLastTime" , formatter.format(TEST_VOLUNTEER.getWorkingLastTime()  ) );

    }

    private void asserts(ResultActions json) throws Exception {

        json.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(TEST_VOLUNTEER.getId()))
                .andExpect(jsonPath("$.name").value(TEST_VOLUNTEER.getName()))
                .andExpect(jsonPath("$.workingFirstTime").value(formatter.format(TEST_VOLUNTEER.getWorkingFirstTime())))
                .andExpect(jsonPath("$.workingLastTime").value(formatter.format(TEST_VOLUNTEER.getWorkingLastTime())))
        ;
    }

    @Test
    public void getVolunteer() throws Exception {
        Mockito.when(volunteerService.find(TEST_VOLUNTEER)).thenReturn(TEST_VOLUNTEER);
        asserts(
                mockMvc.perform(MockMvcRequestBuilders
                        .get(REQUEST_MAPPING_CONTROLLER)
                        .content(TEST_JSON_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON))
        );
    }

    @Test
    public void addVolunteer() throws Exception {
        Mockito.when(volunteerService.add(TEST_VOLUNTEER)).thenReturn(TEST_VOLUNTEER);
        asserts(
            mockMvc.perform(MockMvcRequestBuilders
                            .post(REQUEST_MAPPING_CONTROLLER)
                            .content(TEST_JSON_OBJECT.toString())
                            .contentType(MediaType.APPLICATION_JSON))
        );
    }

    @Test
    public void deleteVolunteer() throws Exception {
        Mockito.when(volunteerService.remove(TEST_VOLUNTEER)).thenReturn(TEST_VOLUNTEER);
        asserts(
            mockMvc.perform(MockMvcRequestBuilders
                            .delete(REQUEST_MAPPING_CONTROLLER)
                            .content(TEST_JSON_OBJECT.toString())
                            .contentType(MediaType.APPLICATION_JSON))
        );
    }

    @Test
    public void editVolunteer() throws Exception {
        Mockito.when(volunteerService.edit(TEST_VOLUNTEER)).thenReturn(TEST_VOLUNTEER);
        asserts(
            mockMvc.perform(MockMvcRequestBuilders
                            .put(REQUEST_MAPPING_CONTROLLER)
                            .content(TEST_JSON_OBJECT.toString())
                            .contentType(MediaType.APPLICATION_JSON))
        );
    }

}