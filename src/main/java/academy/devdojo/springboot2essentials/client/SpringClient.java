package academy.devdojo.springboot2essentials.client;

import academy.devdojo.springboot2essentials.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        log.info("----------Request------");
        ResponseEntity<Anime> response = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 8);
        log.info(response);

        Anime anime = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 8);
        log.info("----------Objeto------");
        log.info(anime);


        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info("----------Lista------");
        log.info(Arrays.toString(animes));

        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all", HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        log.info("----------Lista------");
        log.info(exchange.getBody());
    }
}
