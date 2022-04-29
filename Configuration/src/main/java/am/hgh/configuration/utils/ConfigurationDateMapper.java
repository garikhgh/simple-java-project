package am.hgh.configuration.utils;

import am.hgh.configuration.dto.ConfigurationDto;
import am.hgh.configuration.dto.TagDto;
import am.hgh.configuration.dto.VariableDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ConfigurationDateMapper {

    public ConfigurationDateMapper() {
    }

    public static   ConfigurationDto addLastModifiedDateAndSetConfigId(ConfigurationDto configurationDto) {
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
        return configurationDto;
    }
}
