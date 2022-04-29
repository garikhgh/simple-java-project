package am.hgh.configuration.controllers;

import am.hgh.configuration.dto.ConfigurationDto;
import am.hgh.configuration.entities.ConfigurationEntity;
import am.hgh.configuration.services.ConfigurationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/config")
public class ConfigurationControllers {

    @Autowired
    private ConfigurationServices configurationServices;

    @GetMapping("/all")
    public ResponseEntity<List<ConfigurationEntity>> getAllConfigurations() {

        List<ConfigurationEntity> allConfigs = configurationServices.getAllConfigs();
        return ResponseEntity.ok(allConfigs);
    }

    @GetMapping("/{config_id}")
    public ResponseEntity<ConfigurationEntity> getConfigById(@PathVariable Long config_id) {

        ConfigurationEntity config = configurationServices.getConfigById(config_id);
        return  ResponseEntity.ok(config);
    }

    @PostMapping("/create")
    public ResponseEntity<ConfigurationEntity> createConfiguration(@RequestBody ConfigurationDto configDto) {

        ConfigurationEntity config = configurationServices.createConfig(configDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(config);
    }

    @PutMapping("/update")
    public ResponseEntity<ConfigurationEntity> updateConfiguration(@RequestBody ConfigurationDto configDto) {

        ConfigurationEntity updatedConfig = configurationServices.updateConfig(configDto);
        return ResponseEntity.ok(updatedConfig);
    }

    @DeleteMapping("/{configId}")
    public ResponseEntity<ConfigurationEntity> deleteConfiguration(@PathVariable Long configId) {

        configurationServices.deleteConfigById(configId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{configId}/{variableId}")
    public ResponseEntity<Object> deleteConfigVariable(@PathVariable Long configId, @PathVariable Long variableId) {

        ConfigurationEntity deletedConfigVariable = configurationServices.deleteConfigVariableById(configId, variableId);
        return ResponseEntity.ok(deletedConfigVariable);
    }
}

