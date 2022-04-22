package am.hgh.configuration.Util;

import am.hgh.configuration.dto.ConfigurationDto;
import am.hgh.configuration.dto.TagDto;
import am.hgh.configuration.dto.VariableDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConfigurationSample4Test {

    private ConfigurationDto configurationDto;

    TagDto tagDto = new TagDto(1L, null, null);
    VariableDto variableDto = new VariableDto(1L, "test",
            "description", 111, 555, 444, null, null);
    List<TagDto> tagDtoList = new ArrayList<>();
    List<VariableDto> VariableDtoList = new ArrayList<>();

    public ConfigurationSample4Test() {}

    public ConfigurationDto getConfigurationDto() {
        tagDtoList.add(tagDto);
        VariableDtoList.add(variableDto);
        return  new ConfigurationDto(3L, "dummy test","test description",tagDtoList, VariableDtoList);
        
    }

}
