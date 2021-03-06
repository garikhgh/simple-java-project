package am.hgh.configuration.unitTest.controllers;

import am.hgh.configuration.util.ConfigurationSample4Test;
import am.hgh.configuration.util.LocalDateAdapter;
import am.hgh.configuration.dto.ConfigurationDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ConfigurationControllersTest {

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("getAllConfigurations")
    void getAllConfigurations() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/config/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty());
    }
    @Test
    @DisplayName("getConfigById")
    void getConfigById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/config/{configId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }
    @Test
    @DisplayName("createConfiguration")
    void createConfiguration() throws Exception {
        ConfigurationDto configurationDto = ConfigurationSample4Test.getConfigurationDto(3L,5L,5L);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/config/create")
                        .content(gson.toJson(configurationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3));
    }
    @Test
    @DisplayName("updateConfiguration")
    void updateConfiguration() throws Exception{
        ConfigurationDto configurationDto = ConfigurationSample4Test.getConfigurationDto(2L,2L,2L);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/config/update")
                .content(gson.toJson(configurationDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
//                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("dummy test"));
    }
    @Test
    @DisplayName("deleteConfiguration")
    void deleteConfiguration() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/config/{configId}", 1))
                .andExpect(status().isNoContent());
    }
    @Test
    @DisplayName("deleteConfigVariable")
    void deleteConfigVariable() throws Exception {
        ConfigurationDto configurationDto = ConfigurationSample4Test.getConfigurationDto(1L,1L,1L);
        mockMvc.perform(MockMvcRequestBuilders.patch("/config/1/2")
                .content(gson.toJson(configurationDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }
}