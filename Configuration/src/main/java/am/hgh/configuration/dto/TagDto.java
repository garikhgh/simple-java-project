package am.hgh.configuration.dto;

import com.google.gson.GsonBuilder;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDto {

    private Long id;
    private LocalDate createdAt;
    private LocalDate lastModifiedAt;


}
