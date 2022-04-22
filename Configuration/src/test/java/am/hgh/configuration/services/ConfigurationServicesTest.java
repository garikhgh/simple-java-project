package am.hgh.configuration.services;

import am.hgh.configuration.ConfigurationApplication;
import am.hgh.configuration.entities.ConfigurationEntity;
import am.hgh.configuration.repositories.ConfigurationRepositories;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@TestPropertySource(locations = "resources/application-test.properties")
//@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest( classes = ConfigurationApplication.class, webEnvironment= SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase(replace =AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED, connection = EmbeddedDatabaseConnection.H2)
class ConfigurationServicesTest {
//    resources/application-test.properties
    @Autowired
    private ConfigurationRepositories configurationRepositories;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void getAllConfigs() {

        String url = "/config/all";
        int sizeOfAllConfigurations = 0;


        ResponseEntity<List> res = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null), List.class);
        System.out.println(res);
        Iterable<ConfigurationEntity> listOfConfigurations = configurationRepositories.findAll();

        if (listOfConfigurations instanceof Collection<?>) {
            sizeOfAllConfigurations = ((Collection<?>)listOfConfigurations).size();
        }
        assertThat(sizeOfAllConfigurations).isGreaterThan(0);
    }

    @Test
    void getConfigById() {
        Long configurationId = 0L;
        ConfigurationEntity ConfigById = configurationRepositories.findById(1L).get();
        configurationId  = ConfigById.getId();
        assertThat(configurationId).isEqualTo(1L);

    }

    @Test
    void addConfig() {
    }

    @Test
    void updateConfig() {
    }

    @Test
    void deleteConfigById() {
//        configurationRepositories.deleteById(1);
    }

    @Test
    void deleteConfigVariableById() {
    }
}