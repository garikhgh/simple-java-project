package am.hgh.configuration.utils;

import am.hgh.configuration.dto.ConfigurationDto;
import am.hgh.configuration.dto.TagDto;
import am.hgh.configuration.dto.VariableDto;
import am.hgh.configuration.entities.ConfigurationEntity;
import am.hgh.configuration.mappers.ConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ConfigurationDateMapper {

    @Autowired
    private ConfigMapper configMapper;
    public   ConfigurationEntity addLastModifiedDateAndSetConfigId(ConfigurationDto configurationDto) {

        List<TagDto> tagListsFromFront = configurationDto.getTagListDto();
        List<VariableDto> variablesListFromFront = configurationDto.getVariableListDto();

        for (TagDto tagLastModified: tagListsFromFront) {
            tagLastModified.setLastModifiedAt(LocalDate.now());
        }
        for (VariableDto variableLastModified: variablesListFromFront) {
            variableLastModified.setLastModifiedAt(LocalDate.now());
        }

        configurationDto.setVariableListDto(variablesListFromFront);
        configurationDto.setTagListDto(tagListsFromFront);

        ConfigurationEntity config2Add = configMapper.configDto2Config(configurationDto);
        config2Add.getTagList().forEach(tagEntity->tagEntity.setConfigurationEntity(config2Add));
        config2Add.getVariableList().forEach(variableEntity -> variableEntity.setConfigurationEntity(config2Add));

        return config2Add;
    }
}
