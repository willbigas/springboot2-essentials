package academy.devdojo.springboot2essentials.integration;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.repository.AnimeRepository;
import academy.devdojo.springboot2essentials.requests.AnimePostRequestBody;
import academy.devdojo.springboot2essentials.util.AnimeCreator;
import academy.devdojo.springboot2essentials.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot2essentials.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AnimeControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("list returns list of anime inside page object when sucessful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSucessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        PageableResponse<Anime> animePage = testRestTemplate.exchange("/animes",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageableResponse<Anime>>() {
                }).getBody();

        Assertions.assertThat(animePage)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animePage.stream().toList().get(0).getName()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("findAll returns list of anime inside page object when sucessful")
    void findAll_ReturnsListOfAnimesInsidePageObject_WhenSucessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        List<Anime> animes = testRestTemplate.exchange("/animes/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("findById returns anime when sucessful")
    void findById_ReturnsAnime_WhenSucessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        Long expectedId = savedAnime.getId();

        Anime anime = testRestTemplate.getForObject("/animes/{id}", Anime.class, expectedId);
        Assertions.assertThat(anime)
                .isNotNull();

        Assertions.assertThat(anime.getId())
                .isNotNull()
                .isEqualTo(expectedId);

    }

    @Test
    @DisplayName("findByName returns a list of anime when sucessful")
    void findByName_ReturnsListOfAnime_WhenSucessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        String url = String.format("/animes/find?name=%s", expectedName);
        List<Anime> animes = testRestTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {

        List<Anime> animes = testRestTemplate.exchange("/animes/find?name=dbz",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns anime when sucessful")
    void save_ReturnsAnime_WhenSucessful() {
        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
        ResponseEntity<Anime> entity = testRestTemplate.postForEntity("/animes", animePostRequestBody, Anime.class);
        Assertions.assertThat(entity)
                .isNotNull();

        Assertions.assertThat(entity.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(entity.getBody().getId())
                .isNotNull();

    }

    @Test
    @DisplayName("replace updates anime when sucessful")
    void replace_UpdatesAnime_WhenSucessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        savedAnime.setName("new name");

        ResponseEntity<Void> entity = testRestTemplate.exchange("/animes", HttpMethod.PUT, new HttpEntity<>(savedAnime), Void.class);
        Assertions.assertThat(entity)
                .isNotNull();

        Assertions.assertThat(entity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes anime when sucessful")
    void delete_RemovesAnime_WhenSucessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        ResponseEntity<Void> entity = testRestTemplate.exchange("/animes/{id}", HttpMethod.DELETE, new HttpEntity<>(savedAnime), Void.class, savedAnime.getId());
        Assertions.assertThat(entity)
                .isNotNull();

        Assertions.assertThat(entity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);

    }


}
