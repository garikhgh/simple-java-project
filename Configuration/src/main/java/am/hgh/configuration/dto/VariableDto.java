package am.hgh.configuration.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariableDto {

    private Long id;

    private String name;
    private String description;

    private int minValue;
    private int maxValue;
    private int unit;

    private LocalDate createdAt;
    private LocalDate lastModifiedAt;

}
