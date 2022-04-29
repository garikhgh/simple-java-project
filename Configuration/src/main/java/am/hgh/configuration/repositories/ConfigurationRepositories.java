package am.hgh.configuration.repositories;

import am.hgh.configuration.entities.ConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepositories extends JpaRepository<ConfigurationEntity, Long> {
}
