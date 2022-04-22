package am.hgh.configuration.controllers;

import am.hgh.configuration.dto.ConfigurationDto;
import am.hgh.configuration.entities.ConfigurationEntity;
import am.hgh.configuration.services.ConfigurationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/config")
public class ConfigurationControllers {

    @Autowired
    private ConfigurationServices configurationServices;

    @GetMapping("/all")
    public List<ConfigurationEntity> getAllConfigurations() {
        return configurationServices.getAllConfigs();
    }

    @GetMapping("/{config_id}")
    public ConfigurationEntity getConfigById(@PathVariable Long config_id) {
        return configurationServices.getConfigById(config_id);

    }
    @PostMapping("/add")
    public ResponseEntity<ConfigurationEntity> addConfiguration(@RequestBody ConfigurationDto configDto) {
        return configurationServices.addConfig(configDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ConfigurationEntity> updateConfiguration(@RequestBody ConfigurationDto configDto) {
        return configurationServices.updateConfig(configDto);
    }

    @DeleteMapping("/{configId}")
    public ResponseEntity<Object> deleteConfiguration(@PathVariable Long configId) {

        return configurationServices.deleteConfigById(configId);

    }

    @PatchMapping("/{configId}/{variableId}")
    public ResponseEntity<Object> deleteConfigVariable(@PathVariable Long configId, @PathVariable Long variableId) {

        return configurationServices.deleteConfigVariableById(configId, variableId);
    }


}

