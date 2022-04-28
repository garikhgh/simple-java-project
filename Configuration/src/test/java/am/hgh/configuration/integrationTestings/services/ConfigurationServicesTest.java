package am.hgh.configuration.integrationTestings.services;

import am.hgh.configuration.dto.ConfigurationDto;
import am.hgh.configuration.entities.ConfigurationEntity;
import am.hgh.configuration.entities.VariableEntity;
import am.hgh.configuration.mappers.ConfigMapper;
import am.hgh.configuration.repositories.ConfigurationRepositories;
import am.hgh.configuration.util.ConfigurationSample4Test;
import org.junit.jupiter.api.Assertions;
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
class ConfigurationServicesTest {

    private static final String CONFIGURATION_DOES_NOT_EXIST = "Configuration %d does not exist.";
    @Autowired
    private ConfigurationRepositories configurationRepositories;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private am.hgh.configuration.utils.ConfigurationDateMapper ConfigurationDateMapper;

    @Test
    void getAllConfigs() {

        Iterable<ConfigurationEntity> allConfig = configurationRepositories.findAll();
        Assertions.assertNotNull(allConfig);
    }

    @Test
    void getConfigById() {
        Long configId = 1L;
        ConfigurationEntity configurationEntity = configurationRepositories.findById(configId).get();
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+configurationEntity);
        Assertions.assertNotNull(configurationEntity);
        Assertions.assertNotNull(configurationEntity.getVariableList());
        Assertions.assertNotNull(configurationEntity.getTagList());
    }

    @Test
    void addConfig() {

        ConfigurationSample4Test configurationSample4Test = new ConfigurationSample4Test();
        ConfigurationDto configurationDto = configurationSample4Test.getConfigurationDto(3L,5L,5L);


        ConfigurationEntity  config2Add = ConfigurationDateMapper.addLastModifiedDateAndSetConfigId(configurationDto);

        ConfigurationEntity addedConfig = configurationRepositories.save(config2Add);

        Assertions.assertNotNull(addedConfig);
        Assertions.assertNotNull(addedConfig.getId());
        Assertions.assertNotNull(addedConfig.getVariableList());
        Assertions.assertNotNull(addedConfig.getTagList());
    }

    @Test
    void updateConfig() {
        ConfigurationSample4Test configurationSample4Test = new ConfigurationSample4Test();
        ConfigurationDto configurationDto = configurationSample4Test.getConfigurationDto(2L,3L,3L);

        Long configId = 1L;
        configurationRepositories.findById(configId).orElseThrow(() -> new NullPointerException(String.format(CONFIGURATION_DOES_NOT_EXIST, configId)));
        configurationDto.setId(configId);
        ConfigurationEntity config2Add = ConfigurationDateMapper.addLastModifiedDateAndSetConfigId(configurationDto);
        ConfigurationEntity updatedConfig = configurationRepositories.save(config2Add);
        Assertions.assertEquals(configId, updatedConfig.getId());
        Assertions.assertNotNull(updatedConfig.getVariableList());
        Assertions.assertNotNull(updatedConfig.getTagList());
    }


    @Test
    void deleteConfigById() {

        Long configId = 1L;
        configurationRepositories.findById(configId).orElseThrow(() -> new NullPointerException(String.format(CONFIGURATION_DOES_NOT_EXIST, configId)));
        configurationRepositories.deleteById(configId);
        Optional<ConfigurationEntity> configurationEntity = configurationRepositories.findById(configId);
        Assertions.assertFalse(configurationEntity.isPresent());

    }

    @Test
    void deleteConfigVariableById() {
        Long configId = 2L;
        Long variableId = 1L;
        ConfigurationEntity updatedConfig = null;
        List<VariableEntity> variableEntityList2Remove = new ArrayList<>();
        ConfigurationEntity configFromDb2UpdateVariableById = configurationRepositories.findById(configId).orElseThrow(() -> new NullPointerException(String.format(CONFIGURATION_DOES_NOT_EXIST, configId)));
        List<VariableEntity> configVariableListFromDb = configFromDb2UpdateVariableById.getVariableList();
        for (VariableEntity variable : configVariableListFromDb) {
            if (variable.getId() == variableId) {
                variableEntityList2Remove.add(variable);
            }
        }
        configVariableListFromDb.removeAll(variableEntityList2Remove);
        configFromDb2UpdateVariableById.setVariableList(configVariableListFromDb);

        updatedConfig = configurationRepositories.save(configFromDb2UpdateVariableById);

        Assertions.assertEquals(1, updatedConfig.getVariableList().size());

    }
}