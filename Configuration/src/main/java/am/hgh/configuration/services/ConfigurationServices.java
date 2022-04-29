package am.hgh.configuration.services;

import am.hgh.configuration.dto.ConfigurationDto;
import am.hgh.configuration.entities.ConfigurationEntity;
import am.hgh.configuration.entities.VariableEntity;
import am.hgh.configuration.mappers.ConfigMapper;
import am.hgh.configuration.repositories.ConfigurationRepositories;
import am.hgh.configuration.utils.ConfigurationDateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigurationServices {
    private static final String CONFIGURATION_DOES_NOT_EXIST = "Configuration %d does not exist.";
    @Autowired
    private ConfigurationRepositories configurationRepositories;

    @Autowired
    private ConfigMapper configMapper;

    public List<ConfigurationEntity> getAllConfigs() {
        return (List<ConfigurationEntity>) configurationRepositories.findAll();
    }
    public ConfigurationEntity getConfigById(Long configId) {
        return configurationRepositories.findById(configId).orElseThrow(() -> new NullPointerException(String.format(CONFIGURATION_DOES_NOT_EXIST, configId)));
    }
    public ConfigurationEntity createConfig(ConfigurationDto configurationDto) {
        ConfigurationDto config2Add = ConfigurationDateMapper.addLastModifiedDateAndSetConfigId(configurationDto);
        ConfigurationEntity configEntity2Add = configMapper.configDto2Config(configurationDto);
        configEntity2Add.getTagList().forEach(tagEntity->tagEntity.setConfigurationEntity(configEntity2Add));
        configEntity2Add.getVariableList().forEach(variableEntity -> variableEntity.setConfigurationEntity(configEntity2Add));
        return configurationRepositories.save(configEntity2Add);
    }
    public ConfigurationEntity updateConfig(ConfigurationDto configurationDto) {
            Long configId = configurationDto.getId();
            ConfigurationDto config2Add = ConfigurationDateMapper.addLastModifiedDateAndSetConfigId(configurationDto);
            ConfigurationEntity configEntity2Add = configMapper.configDto2Config(configurationDto);
            configEntity2Add.getTagList().forEach(tagEntity->tagEntity.setConfigurationEntity(configEntity2Add));
            configEntity2Add.getVariableList().forEach(variableEntity -> variableEntity.setConfigurationEntity(configEntity2Add));
            configurationRepositories.findById(configId).orElseThrow(() -> new NullPointerException(String.format(CONFIGURATION_DOES_NOT_EXIST, configId)));
            return configurationRepositories.save(configEntity2Add);
    }
    public void deleteConfigById(Long configId) {
        configurationRepositories.findById(configId).orElseThrow(() -> new NullPointerException(String.format(CONFIGURATION_DOES_NOT_EXIST, configId)));
        configurationRepositories.deleteById(configId);
    }
    public ConfigurationEntity deleteConfigVariableById(Long configId, Long variableId) {
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
        return configurationRepositories.save(configFromDb2UpdateVariableById);
    }
}
