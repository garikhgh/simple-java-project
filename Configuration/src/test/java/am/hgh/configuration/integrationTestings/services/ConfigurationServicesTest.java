package am.hgh.configuration.integrationTestings.services;

import am.hgh.configuration.dto.ConfigurationDto;
import am.hgh.configuration.dto.TagDto;
import am.hgh.configuration.dto.VariableDto;
import am.hgh.configuration.entities.ConfigurationEntity;
import am.hgh.configuration.entities.TagEntity;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;

import java.net.http.HttpHeaders;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("integration")
@TestPropertySource(locations = "classpath:application-integration.properties")
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConfigurationServicesTest {

    @Autowired
    private ConfigurationRepositories configurationRepositories;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ConfigMapper configMapper;

    @Test
    void getAllConfigs() {

        List allConfig = testRestTemplate.getForObject("/config/all", List.class);
        System.out.println(allConfig);
        Assertions.assertNotNull(allConfig);
    }

    @Test
    void getConfigById() {
        Long configId = 1L;
        ConfigurationEntity configurationEntity = configurationRepositories.findById(configId).get();
        System.out.println(configurationEntity.getId());
        Assertions.assertNotNull(configurationEntity);
    }

    @Test
    void addConfig() {
        ConfigurationEntity addedConfig = null;
        ConfigurationSample4Test configurationSample4Test = new ConfigurationSample4Test();
        ConfigurationDto configurationDto = configurationSample4Test.getConfigurationDto();

        List<TagDto> tagListsFromFront = configurationDto.getTagListDto();
        for (TagDto tagCreatedAt : tagListsFromFront) {
            tagCreatedAt.setCreatedAt(LocalDate.now());
            tagCreatedAt.setLastModifiedAt(LocalDate.now());

        }
        configurationDto.setTagListDto(tagListsFromFront);

        List<VariableDto> variablesListFromFront = configurationDto.getVariableListDto();
        for (VariableDto variableCreatedAt: variablesListFromFront) {
            variableCreatedAt.setCreatedAt(LocalDate.now());
            variableCreatedAt.setLastModifiedAt(LocalDate.now());
        }
        configurationDto.setVariableListDto(variablesListFromFront);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+ configurationDto);
        ConfigurationEntity config2Add = configMapper.configDto2Config(configurationDto);

        config2Add.getTagList().forEach(tagEntity->tagEntity.setConfigurationEntity(config2Add));
        config2Add.getVariableList().forEach(variableEntity -> variableEntity.setConfigurationEntity(config2Add));
        addedConfig = configurationRepositories.save(config2Add);

        Assertions.assertNotNull(addedConfig);
        Assertions.assertNotNull(addedConfig.getId());
    }

    @Test
    void updateConfig() {
        ConfigurationSample4Test configurationSample4Test = new ConfigurationSample4Test();
        ConfigurationDto configurationDto = configurationSample4Test.getConfigurationDto();

        Long configId = 1L;
        configurationDto.setId(configId);

        ConfigurationEntity configFromFront2Update = configMapper.configDto2Config(configurationDto);

        ConfigurationEntity updatedConfig = null;
        if (configurationRepositories.existsById(configId)) {
            List<TagEntity> tagListsUpdateLastModifiedAt = configFromFront2Update.getTagList();
            for (TagEntity tagCreatedAt : tagListsUpdateLastModifiedAt) {
                tagCreatedAt.setLastModifiedAt(LocalDate.now());
            }
            List<VariableEntity> variablesListUpdateLastModifiedAt = configFromFront2Update.getVariableList();
            for (VariableEntity variableCreatedAt : variablesListUpdateLastModifiedAt) {
                variableCreatedAt.setLastModifiedAt(LocalDate.now());

            }
            configFromFront2Update.setTagList(tagListsUpdateLastModifiedAt);
            configFromFront2Update.setVariableList(variablesListUpdateLastModifiedAt);

            configFromFront2Update.getTagList().forEach(tagEntity -> tagEntity.setConfigurationEntity(configFromFront2Update));
            configFromFront2Update.getVariableList().forEach(variableEntity -> variableEntity.setConfigurationEntity(configFromFront2Update));

            updatedConfig = configurationRepositories.save(configFromFront2Update);

        }
        Assertions.assertEquals(configId, updatedConfig.getId());
    }



    @Test
    void deleteConfigById() {

        Long configId = 1L;
        configurationRepositories.deleteById(configId);

        Optional<ConfigurationEntity> configurationEntity = configurationRepositories.findById(configId);
        Assertions.assertFalse(configurationEntity.isPresent());


    }

    @Test
    void deleteConfigVariableById() {
        int configId = 1;
        int variableId = 1;
        ConfigurationEntity configurationEntity = testRestTemplate.getForObject("/config/"+ configId +"/" + variableId, ConfigurationEntity.class);
        List<VariableEntity> variableEntityList = configurationEntity.getVariableList();
//        variableEntityList.remove(variableId);
//        configurationEntity.setVariableList(variableEntityList);
        System.out.println(configurationEntity);
//        testRestTemplate.put("/config/update", configurationEntity);
//        ConfigurationEntity configurationEntity1 = testRestTemplate.getForObject("/config/"+ configId, ConfigurationEntity.class);
//        Assertions.assertNotNull(configurationEntity1);
    }
}