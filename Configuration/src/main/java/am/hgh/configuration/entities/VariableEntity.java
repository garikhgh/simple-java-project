package am.hgh.configuration.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "configuration_variable")
public class VariableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String description;
    private int minValue;
    private int maxValue;
    private int unit;

    private LocalDate createdAt;
    private LocalDate lastModifiedAt;

    @ManyToOne
    @JsonBackReference
    private ConfigurationEntity configurationEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VariableEntity variable = (VariableEntity) o;
        return Objects.equals(id, variable.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "VariableEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", unit=" + unit +
                ", createdAt=" + createdAt +
                ", lastModifiedAt=" + lastModifiedAt +
                ", configurationEntity=" + configurationEntity +
                '}';
    }
}
