package am.hgh.configuration.repositories;

import am.hgh.configuration.entities.ConfigurationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepositories extends CrudRepository<ConfigurationEntity, Long> {
}
