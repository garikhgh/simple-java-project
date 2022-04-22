package am.hgh.configuration.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "configuration")
public class ConfigurationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String description;


    @OneToMany(targetEntity = TagEntity.class, mappedBy = "configurationEntity", cascade=CascadeType.ALL)
    @JsonManagedReference
    @Singular private List<TagEntity> tagList;

    @OneToMany(targetEntity = VariableEntity.class, mappedBy = "configurationEntity", cascade=CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Singular  private List<VariableEntity> variableList;

}
