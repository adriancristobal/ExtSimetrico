package config;

import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;
import utils.PropertiesConstants;

import java.io.InputStream;
import java.util.Map;

@Getter
@Log4j2
@Singleton
public class ConfigJDBCProperties {

    private String ruta;
    private String user;
    private String password;
    private String driver;




    public ConfigJDBCProperties() {
        Yaml yaml = new Yaml();
        InputStream inputStream = null;

        inputStream = getClass().getClassLoader().getResourceAsStream(PropertiesConstants.PATH_YAML);
        Map<String, Object> obj = yaml.load(inputStream);
        ruta = (String) obj.get(PropertiesConstants.RUTA);
        user = (String) obj.get(PropertiesConstants.USER);
        password = (String) obj.get(PropertiesConstants.PASSWORD);
        driver = (String) obj.get(PropertiesConstants.DRIVER);

    }


}
