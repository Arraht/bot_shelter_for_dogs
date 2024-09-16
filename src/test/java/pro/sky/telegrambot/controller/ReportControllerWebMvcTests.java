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
import pro.sky.telegrambot.entity.Client;
import pro.sky.telegrambot.entity.Dog;
import pro.sky.telegrambot.entity.Report;
import pro.sky.telegrambot.entity.Volunteers;
import pro.sky.telegrambot.interfaces.shelter.ReportService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
public class ReportControllerWebMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    private final String REQUEST_MAPPING_CONTROLLER = "/report";
    private final String SLASH = "/";

    private final Report TEST_REPORT = new Report();
    private final Report TEST_REPORT_WITH_NULL_ID = new Report();

    private Report EXPEPTED_REPORT = new Report();
    private JSONObject TEST_JSON_OBJECT;

    private LocalDateTime TEST_LOCAL_DATE_TIME = LocalDateTime.now();

    //    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnnn");
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
//    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");


    @BeforeEach
    void init_report() throws JSONException {
        TEST_REPORT.setId(1l);
        TEST_REPORT.setClient(new Client());
        TEST_REPORT.setPet(new Dog());
        TEST_REPORT.setAccepted(true);
        TEST_REPORT.setWhoAccepted(new Volunteers());
        TEST_REPORT.setTextReportFromClient("Test message from client");

        TEST_REPORT.setTimeCreated(TEST_LOCAL_DATE_TIME);
        TEST_REPORT.setTimeReceivedPhoto(TEST_LOCAL_DATE_TIME);
        TEST_REPORT.setTimeReceivedText(TEST_LOCAL_DATE_TIME);


        TEST_JSON_OBJECT = new JSONObject();
        TEST_JSON_OBJECT.put("id", TEST_REPORT.getId());
        TEST_JSON_OBJECT.put("client", TEST_REPORT.getClient());
        TEST_JSON_OBJECT.put("pet", TEST_REPORT.getPet());
        TEST_JSON_OBJECT.put("accepted", TEST_REPORT.getAccepted());
        TEST_JSON_OBJECT.put("whoAccepted", TEST_REPORT.getWhoAccepted());
        TEST_JSON_OBJECT.put("textReportFromClient", TEST_REPORT.getTextReportFromClient());

        TEST_JSON_OBJECT.put("timeCreated", formatter.format(TEST_REPORT.getTimeCreated() ) );
        TEST_JSON_OBJECT.put("timeReceivedPhoto" , formatter.format(TEST_REPORT.getTimeReceivedPhoto()  ) );
        TEST_JSON_OBJECT.put("timeReceivedText" , formatter.format(TEST_REPORT.getTimeReceivedText()  ) );
    }

    private void asserts(ResultActions json) throws Exception {

        json.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(TEST_REPORT.getId()))
                .andExpect(jsonPath("$.client").value(TEST_REPORT.getClient()))
                .andExpect(jsonPath("$.pet").value(TEST_REPORT.getPet()))
                .andExpect(jsonPath("$.accepted").value(TEST_REPORT.getAccepted()))
                .andExpect(jsonPath("$.whoAccepted").value(TEST_REPORT.getWhoAccepted()))
                .andExpect(jsonPath("$.textReportFromClient").value(TEST_REPORT.getTextReportFromClient()))
                .andExpect(jsonPath("$.timeCreated").value(TEST_REPORT.getTimeCreated()))
                .andExpect(jsonPath("$.timeReceivedPhoto").value(TEST_REPORT.getTimeReceivedPhoto()))
                .andExpect(jsonPath("$.timeReceivedText").value(TEST_REPORT.getTimeReceivedText()))
        ;
    }

    @Test
    public void getReport() throws Exception {
        Mockito.when(reportService.find(TEST_REPORT)).thenReturn(TEST_REPORT);
        asserts(
                mockMvc.perform(MockMvcRequestBuilders
                        .get(REQUEST_MAPPING_CONTROLLER)
                        .content(TEST_JSON_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON))
        );
    }

    @Test
    public void addReport() throws Exception {
        Mockito.when(reportService.add(TEST_REPORT)).thenReturn(TEST_REPORT);
        asserts(
                mockMvc.perform(MockMvcRequestBuilders
                        .post(REQUEST_MAPPING_CONTROLLER)
                        .content(TEST_JSON_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON))
        );
    }

    @Test
    public void deleteReport() throws Exception {
        Mockito.when(reportService.remove(TEST_REPORT)).thenReturn(TEST_REPORT);
        asserts(
                mockMvc.perform(MockMvcRequestBuilders
                        .delete(REQUEST_MAPPING_CONTROLLER)
                        .content(TEST_JSON_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON))
        );
    }

    @Test
    public void editReport() throws Exception {
        Mockito.when(reportService.edit(TEST_REPORT)).thenReturn(TEST_REPORT);
        asserts(
                mockMvc.perform(MockMvcRequestBuilders
                        .put(REQUEST_MAPPING_CONTROLLER)
                        .content(TEST_JSON_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON))
        );
    }

}
