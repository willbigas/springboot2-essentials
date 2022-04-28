package academy.devdojo.springboot2essentials.client;

import academy.devdojo.springboot2essentials.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {

        getRequestFindByIDReturnResponse();

        getRequestFindByIDReturnObject();

        getRequestFindAllReturnArray();

        getRequestFindAllReturnResponseList();

        postRequestReturnEntity();

        postRequestReturnExchange();
    }

    private static void postRequestReturnEntity() {
        Anime kingdom = Anime.builder()
                .name("kingdom")
                .build();

        Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes/", kingdom, Anime.class);
        log.info("saved anime {} " , kingdomSaved);
    }

    private static void postRequestReturnExchange() {
        Anime samuraiChamploo = Anime.builder()
                .name("Samurai Champloo")
                .build();

        ResponseEntity<Anime> exchange = new RestTemplate().exchange("http://localhost:8080/animes/", HttpMethod.POST, new HttpEntity<>(samuraiChamploo , createJsonHeader()), Anime.class);
        log.info("saved anime {} " , exchange.getBody());
    }

    private static void getRequestFindAllReturnResponseList() {
        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all", HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        log.info("----------Lista------");
        log.info(exchange.getBody());
    }

    private static void getRequestFindAllReturnArray() {
        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info("----------Lista------");
        log.info(Arrays.toString(animes));
    }

    private static void getRequestFindByIDReturnObject() {
        Anime anime = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 8);
        log.info("----------Objeto------");
        log.info(anime);
    }

    private static void getRequestFindByIDReturnResponse() {
        log.info("----------Request------");
        ResponseEntity<Anime> response = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 8);
        log.info(response);
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
