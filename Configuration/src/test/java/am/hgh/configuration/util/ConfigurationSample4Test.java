package am.hgh.configuration.util;

import am.hgh.configuration.dto.ConfigurationDto;
import am.hgh.configuration.dto.TagDto;
import am.hgh.configuration.dto.VariableDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConfigurationSample4Test {

    private ConfigurationDto configurationDto;

    TagDto tagDto = new TagDto(5L, null, null);
    VariableDto variableDto = new VariableDto(5L, "test",
            "description", 111, 555, 444, null, null);
    List<TagDto> tagListDto = new ArrayList<>();
    List<VariableDto> VariableListDto = new ArrayList<>();

    public ConfigurationSample4Test() {}

    public ConfigurationDto getConfigurationDto() {
        tagListDto.add(tagDto);
        VariableListDto.add(variableDto);
        return  new ConfigurationDto(4L, "dummy test","test description",tagListDto, VariableListDto);
        
    }

}