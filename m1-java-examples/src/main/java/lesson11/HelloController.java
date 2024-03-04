package lesson11;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @RequestMapping("/hello")
    public ResponseEntity<String> getHelloWorld(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "locale") String locale
    ) {
        return ResponseEntity.ok(helloService.sayHello(name, locale));
    }

}
