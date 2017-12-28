import com.surowka.swapi.service.ApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=Application.class)
public class ApiServiceTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    HttpEntity<String> requestEntity;

    @Test
    public void test_should_return_nonempty_list_of_star_wars_species() {

        ApiService apiService = new ApiService(restTemplate, requestEntity);
        List<String> response = apiService.getAllStarWarsSpecies();
        assertFalse(response.isEmpty());
    }

    @Test
    public void test_should_return_nonempty_list_of_pokemon_species() {

        ApiService apiService = new ApiService(restTemplate, requestEntity);
        List<String> response = apiService.getAllPokemonSpecies();
        assertFalse(response.isEmpty());
    }
}
