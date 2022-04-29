package am.hgh.configuration;

import am.hgh.configuration.controllers.ConfigurationControllers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
@ActiveProfiles("test")
@SpringBootTest
class ConfigurationApplicationTests {
    @Autowired
    private ConfigurationControllers configurationControllers;
    @Test
    void contextLoads() {
        assertThat(configurationControllers).isNotNull();
    }
}
