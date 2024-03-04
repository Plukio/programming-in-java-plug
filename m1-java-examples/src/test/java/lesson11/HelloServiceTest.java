package lesson11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloServiceTest {

    private final HelloRepository helloRepository = Mockito.mock(HelloRepository.class);

    private final HelloService helloService = new HelloService(helloRepository);

    @Test
    @DisplayName("should return localized hello")
    public void testHelloService() {
        Mockito.when(helloRepository.selectHelloInLanguage("en")).thenReturn("Hello");

        String result = helloService.sayHello("Monika", "en");
        assertEquals("Hello Monika!", result);

        Mockito.verify(helloRepository).selectHelloInLanguage("en");
    }

}
