package am.hgh.configuration.mappers;

import am.hgh.configuration.dto.ConfigurationDto;
import am.hgh.configuration.entities.ConfigurationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ConfigMapper {

    ConfigMapper INSTANCE = Mappers.getMapper(ConfigMapper.class);
@Mapping(target = "variableListDto", source = "variableList")
@Mapping(target = "tagListDto", source = "tagList")
ConfigurationDto config2ConfigDto(ConfigurationEntity configurationEntity);

@Mapping(target = "variableList", source = "variableListDto")
@Mapping(target = "tagList", source = "tagListDto")
ConfigurationEntity configDto2Config(ConfigurationDto configurationDto);





}
