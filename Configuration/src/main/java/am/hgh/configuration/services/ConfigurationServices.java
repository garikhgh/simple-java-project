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

    @Autowired
    private ConfigurationDateMapper ConfigurationDateMapper;

    public List<ConfigurationEntity> getAllConfigs() {
        return (List<ConfigurationEntity>) configurationRepositories.findAll();
    }
    public ConfigurationEntity getConfigById(Long configId) {
        return configurationRepositories.findById(configId).orElseThrow(() -> new NullPointerException(String.format(CONFIGURATION_DOES_NOT_EXIST, configId)));

    }
    public ConfigurationEntity createConfig(ConfigurationDto configurationDto) {

        ConfigurationEntity config2Add = ConfigurationDateMapper.addLastModifiedDateAndSetConfigId(configurationDto);
        return configurationRepositories.save(config2Add);
    }
    public ConfigurationEntity updateConfig(ConfigurationDto configurationDto) {

            Long configId = configurationDto.getId();
            ConfigurationEntity config2Add = ConfigurationDateMapper.addLastModifiedDateAndSetConfigId(configurationDto);
            configurationRepositories.findById(configId).orElseThrow(() -> new NullPointerException(String.format(CONFIGURATION_DOES_NOT_EXIST, configId)));
            return configurationRepositories.save(config2Add);
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
            if (variable.getId() == variableId) {
                variableEntityList2Remove.add(variable);
            }
        }
        configVariableListFromDb.removeAll(variableEntityList2Remove);
        configFromDb2UpdateVariableById.setVariableList(configVariableListFromDb);
        return configurationRepositories.save(configFromDb2UpdateVariableById);
    }
}
