package am.hgh.configuration.services;

import am.hgh.configuration.dto.ConfigurationDto;
import am.hgh.configuration.dto.TagDto;
import am.hgh.configuration.dto.VariableDto;
import am.hgh.configuration.entities.ConfigurationEntity;
import am.hgh.configuration.entities.TagEntity;
import am.hgh.configuration.entities.VariableEntity;
import am.hgh.configuration.mappers.ConfigMapper;
import am.hgh.configuration.repositories.ConfigurationRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ConfigurationServices {

    @Autowired
    private ConfigurationRepositories configurationRepositories;

    @Autowired
    private ConfigMapper configMapper;

    public List<ConfigurationEntity> getAllConfigs() {
        return (List<ConfigurationEntity>) configurationRepositories.findAll();
    }
    public ResponseEntity<ConfigurationEntity> getConfigById(Long configId) {
        try {
            ConfigurationEntity configurationObjEntity = configurationRepositories.findById(configId).get();
            return new ResponseEntity<>(configurationObjEntity, HttpStatus.OK);

        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }
    public ResponseEntity<ConfigurationEntity> addConfig(ConfigurationDto configurationDto) {

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

        ConfigurationEntity config2Add = configMapper.configDto2Config(configurationDto);
        config2Add.getTagList().forEach(tagEntity->tagEntity.setConfigurationEntity(config2Add));
        config2Add.getVariableList().forEach(variableEntity -> variableEntity.setConfigurationEntity(config2Add));

        try {
            ConfigurationEntity addedConfig = configurationRepositories.save(config2Add);
            return new ResponseEntity<>(addedConfig, HttpStatus.OK);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<ConfigurationEntity> updateConfig(ConfigurationDto configurationDto) {

        Long configId = configurationDto.getId();
        ConfigurationEntity configFromFront2Update = configMapper.configDto2Config(configurationDto);

        try {
            if (configurationRepositories.existsById(configId)) {

                List<TagEntity> tagListsUpdateLastModifiedAt = configFromFront2Update.getTagList();
                for (TagEntity tagCreatedAt : tagListsUpdateLastModifiedAt) {
                    tagCreatedAt.setLastModifiedAt(LocalDate.now());
                }
                List<VariableEntity> variablesListUpdateLastModifiedAt = configFromFront2Update.getVariableList();
                for (VariableEntity variableCreatedAt: variablesListUpdateLastModifiedAt) {
                    variableCreatedAt.setLastModifiedAt(LocalDate.now());

                }
                configFromFront2Update.setTagList(tagListsUpdateLastModifiedAt);
                configFromFront2Update.setVariableList(variablesListUpdateLastModifiedAt);

                configFromFront2Update.getTagList().forEach(tagEntity->tagEntity.setConfigurationEntity(configFromFront2Update));
                configFromFront2Update.getVariableList().forEach(variableEntity -> variableEntity.setConfigurationEntity(configFromFront2Update));

                ConfigurationEntity updatedConfig = configurationRepositories.save(configFromFront2Update);
                return new ResponseEntity<>(updatedConfig, HttpStatus.FOUND);

            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch(IllegalArgumentException | NoSuchElementException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> deleteConfigById(Long configId) {
        try {
            if (configurationRepositories.existsById(configId)) {
                configurationRepositories.deleteById(configId);
                return new ResponseEntity<>("Configuration is deleted", HttpStatus.OK);

            } else {
                return new ResponseEntity<>("Configuration is Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }
    }

    public ResponseEntity<Object> deleteConfigVariableById(Long configId, Long variableId) {
        List<VariableEntity> variableEntityList2Remove = new ArrayList<>();
        ResponseEntity<Object> response = null;

        try {
            if (configurationRepositories.existsById(configId)) {

                ConfigurationEntity configFromDb2UpdateVariableById = configurationRepositories.findById(configId).get();
                List<VariableEntity> configVariableListFromDb = configFromDb2UpdateVariableById.getVariableList();

                for(VariableEntity variable: configVariableListFromDb) {

                    if (variable.getId() == variableId) {
                        variableEntityList2Remove.add(variable);
                        }
                    }
                configVariableListFromDb.removeAll(variableEntityList2Remove);
                configFromDb2UpdateVariableById.setVariableList(configVariableListFromDb);

                ConfigurationEntity updateConfig = configurationRepositories.save(configFromDb2UpdateVariableById);
                response = new ResponseEntity<>(updateConfig, HttpStatus.OK);

                }
        } catch (IllegalArgumentException  e) {
            response = new ResponseEntity<>("Nothing Happened", HttpStatus.NOT_FOUND);
        }
        return response;
    }
}
