package academy.devdojo.springboot2essentials.service;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.repository.AnimeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnimeService {

    public List<Anime> listAll() {
        return List.of(new Anime(1L, "Boku no Hero"), new Anime(2L, "Berserk"));
    }
}