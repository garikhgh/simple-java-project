package am.hgh.configuration.integrationTests.repositories;

import am.hgh.configuration.dto.ConfigurationDto;
import am.hgh.configuration.entities.ConfigurationEntity;
import am.hgh.configuration.entities.VariableEntity;
import am.hgh.configuration.mappers.ConfigMapper;
import am.hgh.configuration.repositories.ConfigurationRepositories;
import am.hgh.configuration.util.ConfigurationSample4Test;
import am.hgh.configuration.utils.ConfigurationDateMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ActiveProfiles("integration")
@TestPropertySource(locations = "classpath:application-integration.properties")
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConfigurationRepositoryTest {

    private static final String CONFIGURATION_DOES_NOT_EXIST = "Configuration %d does not exist.";
    @Autowired
    private ConfigurationRepositories configurationRepositories;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ConfigMapper configMapper;

    @Test
    @DisplayName("getAllConfigs")
    void getAllConfigs() {
        Iterable<ConfigurationEntity> allConfig = configurationRepositories.findAll();
        Assertions.assertNotNull(allConfig);
    }

    @Test
    @DisplayName("getConfigById")
    void getConfigById() {
        Long configId = 1L;
        ConfigurationEntity configurationEntity = configurationRepositories.findById(configId).get();
        Assertions.assertNotNull(configurationEntity);
        Assertions.assertNotNull(configurationEntity.getVariableList());
        Assertions.assertNotNull(configurationEntity.getTagList());
    }
    @Test
    @DisplayName("createConfig")
    void createConfig() {
        ConfigurationDto configurationDto = ConfigurationSample4Test.getConfigurationDto(3L,5L,5L);
        ConfigurationDto  config2Add = ConfigurationDateMapper.addLastModifiedDateAndSetConfigId(configurationDto);
        ConfigurationEntity configEntity2Add = configMapper.configDto2Config(configurationDto);
        configEntity2Add.getTagList().forEach(tagEntity->tagEntity.setConfigurationEntity(configEntity2Add));
        configEntity2Add.getVariableList().forEach(variableEntity -> variableEntity.setConfigurationEntity(configEntity2Add));
        ConfigurationEntity addedConfig = configurationRepositories.save(configEntity2Add);
        Assertions.assertNotNull(addedConfig);
        Assertions.assertNotNull(addedConfig.getId());
        Assertions.assertNotNull(addedConfig.getVariableList());
        Assertions.assertNotNull(addedConfig.getTagList());
    }

    @Test
    @DisplayName("updateConfig")
    void updateConfig() {
        Long configId = 1L;
        ConfigurationDto configurationDto = ConfigurationSample4Test.getConfigurationDto(configId,3L,3L);
        configurationRepositories.findById(configId).orElseThrow(() -> new NullPointerException(String.format(CONFIGURATION_DOES_NOT_EXIST, configId)));
        ConfigurationDto config2Add = ConfigurationDateMapper.addLastModifiedDateAndSetConfigId(configurationDto);
        ConfigurationEntity configEntity2Add = configMapper.configDto2Config(configurationDto);
        configEntity2Add.getTagList().forEach(tagEntity->tagEntity.setConfigurationEntity(configEntity2Add));
        configEntity2Add.getVariableList().forEach(variableEntity -> variableEntity.setConfigurationEntity(configEntity2Add));
        ConfigurationEntity updatedConfig = configurationRepositories.save(configEntity2Add);
        Assertions.assertEquals(configId, updatedConfig.getId());
        Assertions.assertNotNull(updatedConfig.getVariableList());
        Assertions.assertNotNull(updatedConfig.getTagList());
    }

    @Test
    @DisplayName("deleteConfigById")
    void deleteConfigById() {
        Long configId = 1L;
        configurationRepositories.findById(configId).orElseThrow(() -> new NullPointerException(String.format(CONFIGURATION_DOES_NOT_EXIST, configId)));
        configurationRepositories.deleteById(configId);
        Optional<ConfigurationEntity> configurationEntity = configurationRepositories.findById(configId);
        Assertions.assertFalse(configurationEntity.isPresent());
    }
    @Test
    @DisplayName("deleteConfigVariableById")
    void deleteConfigVariableById() {
        Long configId = 2L;
        Long variableId = 1L;
        ConfigurationEntity updatedConfig = null;
        List<VariableEntity> variableEntityList2Remove = new ArrayList<>();
        ConfigurationEntity configFromDb2UpdateVariableById = configurationRepositories.findById(configId).orElseThrow(() -> new NullPointerException(String.format(CONFIGURATION_DOES_NOT_EXIST, configId)));
        List<VariableEntity> configVariableListFromDb = configFromDb2UpdateVariableById.getVariableList();
        for (VariableEntity variable : configVariableListFromDb) {
            if (variable.getId().equals(variableId)) {
                variableEntityList2Remove.add(variable);
            }
        }
        configVariableListFromDb.removeAll(variableEntityList2Remove);
        configFromDb2UpdateVariableById.setVariableList(configVariableListFromDb);
        updatedConfig = configurationRepositories.save(configFromDb2UpdateVariableById);
        Assertions.assertEquals(1, updatedConfig.getVariableList().size());

    }
}