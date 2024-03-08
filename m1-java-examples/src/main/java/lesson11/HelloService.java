package lesson11;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    /*
    Options 1: field injection
     */
    @Value("${hello.default-name}")
    private String defaultName;

    @Value("${hello.default-locale}")
    private String defaultLocale;

    private final HelloRepository helloRepository;

    /*
    Options 2: constructor injection of a configuration object
     */
    private final HelloConfigurationProperties config;

    public HelloService(HelloRepository helloRepository, HelloConfigurationProperties config) {
        this.helloRepository = helloRepository;
        this.config = config;
    }

//    public String sayHello(String name, String locale) {
//        String hello = helloRepository.selectHelloInLanguage(locale != null ? locale : defaultLocale);
//        return String.format("%s %s!", hello, name != null ? name : defaultName);
//    }

    public String sayHello(String name, String locale) {
        String hello = helloRepository.selectHelloInLanguage(locale != null ? locale : config.getDefaultLocale());
        return String.format("%s %s!", hello, name != null ? name : config.getDefaultName());
    }

}
