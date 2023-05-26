package common.config;

import common.utils.StringsUtils;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

@Log4j2
@Singleton
@Getter
@Setter
public class ConfigSingleton_Client {

    private String path_base;

    private ConfigSingleton_Client(){
        try{
            Yaml yaml = new Yaml();

            Iterable<Object> it = null;

            it = yaml.loadAll(new FileInputStream(StringsUtils.YAML_PATH));

            Map<String, String> map = (Map<String, String>) it.iterator().next();
            this.setPath_base(map.get(StringsUtils.PATH_BASE));
            //this.setPath_base(map.get(StringsUtils.PATH_BASE_SPRING));

        }catch (FileNotFoundException ex){
            log.error(ex.getMessage());
        }
    }
}
