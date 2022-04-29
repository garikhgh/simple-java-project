package am.hgh.configuration.integrationTests.services;

import am.hgh.configuration.dto.ConfigurationDto;
import am.hgh.configuration.entities.ConfigurationEntity;
import am.hgh.configuration.mappers.ConfigMapper;
import am.hgh.configuration.services.ConfigurationServices;
import am.hgh.configuration.util.ConfigurationSample4Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@ActiveProfiles("integration")
@TestPropertySource(locations = "classpath:application-integration.properties")
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConfigurationServicesTest {
    @Autowired
    private ConfigurationServices configurationServices;
    @Autowired
    private ConfigMapper configMapper;

    @Test
    @DisplayName("getAllConfigs")
    void getAllConfigs() {
        List<ConfigurationEntity> allConfigs = configurationServices.getAllConfigs();
        Assertions.assertNotNull(allConfigs.size());
    }

    @Test
    @DisplayName("getConfigById")
    void getConfigById() {
        ConfigurationEntity getConfig = configurationServices.getConfigById(2L);
        Assertions.assertNotNull(getConfig);
        Assertions.assertEquals(getConfig.getId(), 2L);
    }

    @Test
    @DisplayName("createConfig")
    void createConfig() {
        ConfigurationDto configurationDto = ConfigurationSample4Test.getConfigurationDto(3L,4L,5L);
        ConfigurationEntity createdEntity = configurationServices.createConfig(configurationDto);
        Assertions.assertNotNull(createdEntity);
        Assertions.assertEquals(createdEntity.getId(), 1L);
    }

    @Test
    @DisplayName("updateConfig")
    void updateConfig() {
        ConfigurationDto configurationDto = ConfigurationSample4Test.getConfigurationDto(2L,1L,1L);
        ConfigurationEntity updatedConfig = configurationServices.updateConfig(configurationDto);
        Assertions.assertNotNull(updatedConfig.getId());
        Assertions.assertEquals(updatedConfig.getDescription(), "test description");
    }

    @Test
    @DisplayName("deleteConfigById")
    void deleteConfigById() {
        configurationServices.deleteConfigById(1L);
        ConfigurationEntity deletedConfig = configurationServices.getConfigById(1L);
        Assertions.assertNull(deletedConfig);
    }

    @Test
    @DisplayName("deleteConfigVariableById")
    void deleteConfigVariableById() {
        Long configId = 2L;
        Long variableId = 1L;
        ConfigurationEntity configDeletedVariable = configurationServices.deleteConfigVariableById(configId, variableId);
        Assertions.assertEquals(configDeletedVariable.getId(), 2L);
        Assertions.assertEquals(configDeletedVariable.getVariableList().size(), 0L);
    }
}