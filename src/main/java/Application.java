import com.surowka.service.GuiConsole;
import com.surowka.service.QuizMaker;
import com.surowka.service.RestDataExecutor;
import com.surowka.swapi.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Scanner;

@SpringBootApplication(scanBasePackages = "com.surowka")
public class Application {

    @Autowired
    private ApiService apiService;
    @Autowired
    private RestDataExecutor restDataExecutor;
    @Autowired
    private QuizMaker quizMaker;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public GuiConsole guiConsole() {
        return new GuiConsole(new Scanner(System.in));
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        Instant start = Instant.now();
        return args -> {
            log.info("Quiz Application has started.");
            quizMaker.prepareQuiz(restDataExecutor.fetchRestData()).setGuiConsole(guiConsole()).startQuiz();
            log.info("Application start time: {} ms", Duration.between(start, Instant.now()).toMillis());
        };
    }

    @Bean
    public HttpEntity<String> requestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        return new HttpEntity<String>("parameters", headers);
    }
}
