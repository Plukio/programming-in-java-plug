package lesson11;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "hello")
public class HelloConfigurationProperties {

    private String defaultName;
    private String defaultLocale;

    public String getDefaultName() {
        return defaultName;
    }

    public String getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public void setDefaultLocale(String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }
}


