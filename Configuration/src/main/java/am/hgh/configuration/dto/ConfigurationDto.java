package am.hgh.configuration.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ConfigurationDto {

    private Long id;
    private String name;
    private String description;
    @Singular
    private List<TagDto> tagListDto;
    @Singular
    private List<VariableDto> variableListDto;

    @Override
    public String toString() {
        return "ConfigurationDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tagListDto=" + tagListDto +
                ", variableListDto=" + variableListDto +
                '}';
    }
}
