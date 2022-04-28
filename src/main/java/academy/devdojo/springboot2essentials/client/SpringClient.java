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

    public static final String SAVED_ANIME = "saved anime {} ";
    public static final String KINGDOM = "kingdom";
    public static final String LISTA = "----------Lista------";
    public static final String PATH_ANIMES = "http://localhost:8080/animes/";
    public static final String PATH_ANIMES_WITH_ID = "http://localhost:8080/animes/{id}";

    public static void main(String[] args) {

        getRequestFindByIDReturnResponse();

        getRequestFindByIDReturnObject();

        getRequestFindAllReturnArray();

        getRequestFindAllReturnResponseList();

        postRequestReturnEntity();

        postRequestReturnExchange();

        putRequestReturnExchange();

        deleteRequestReturnExchange();
    }

    private static void deleteRequestReturnExchange() {
        Anime animeToBeDeleted = postRequestReturnEntitySaved();

        ResponseEntity<Void> exchange = new RestTemplate().exchange(PATH_ANIMES_WITH_ID, HttpMethod.DELETE, null, Void.class , animeToBeDeleted.getId());
        log.info("deleted anime {} " , exchange.getStatusCode());
    }

    private static void putRequestReturnExchange() {
        Anime animeToBeUpdated = postRequestReturnEntitySaved();
        animeToBeUpdated.setName("Nome Alterado");

        ResponseEntity<Void> exchange = new RestTemplate().exchange(PATH_ANIMES, HttpMethod.PUT, new HttpEntity<>(animeToBeUpdated , createJsonHeader()), Void.class);
        log.info("updated anime {} " , exchange.getStatusCode());
    }

    private static void postRequestReturnEntity() {
        Anime kingdom = Anime.builder()
                .name(KINGDOM)
                .build();

        Anime kingdomSaved = new RestTemplate().postForObject(PATH_ANIMES, kingdom, Anime.class);
        log.info(SAVED_ANIME, kingdomSaved);
    }

    private static Anime postRequestReturnEntitySaved() {
        Anime kingdom = Anime.builder()
                .name(KINGDOM)
                .build();

        Anime kingdomSaved = new RestTemplate().postForObject(PATH_ANIMES, kingdom, Anime.class);
        log.info(SAVED_ANIME , kingdomSaved);
        return kingdomSaved;
    }

    private static void postRequestReturnExchange() {
        Anime samuraiChamploo = Anime.builder()
                .name("Samurai Champloo")
                .build();

        ResponseEntity<Anime> exchange = new RestTemplate().exchange(PATH_ANIMES, HttpMethod.POST, new HttpEntity<>(samuraiChamploo , createJsonHeader()), Anime.class);
        log.info(SAVED_ANIME , exchange.getBody());
    }

    private static void getRequestFindAllReturnResponseList() {
        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all", HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        log.info(LISTA);
        log.info(exchange.getBody());
    }

    private static void getRequestFindAllReturnArray() {
        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info(LISTA);
        log.info(Arrays.toString(animes));
    }

    private static void getRequestFindByIDReturnObject() {
        Anime anime = new RestTemplate().getForObject(PATH_ANIMES_WITH_ID, Anime.class, 8);
        log.info("----------Objeto------");
        log.info(anime);
    }

    private static void getRequestFindByIDReturnResponse() {
        log.info("----------Request------");
        ResponseEntity<Anime> response = new RestTemplate().getForEntity(PATH_ANIMES_WITH_ID, Anime.class, 8);
        log.info(response);
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
