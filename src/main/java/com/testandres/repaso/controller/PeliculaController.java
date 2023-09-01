package com.testandres.repaso.controller;

import com.testandres.repaso.model.Result;
import com.testandres.repaso.model.ResultResponse;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api")
public class PeliculaController {

    @Autowired
    private RestTemplate restTemplate;

    public UriComponentsBuilder Api_movie(String text) {
        String apiUrl = "https://api.themoviedb.org/3/search/multi";  // URL del API Movie

        //  'api_key': '3e56846ee7cfb0b7d870484a9f66218c'
        // 'query'
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("api_key", "3e56846ee7cfb0b7d870484a9f66218c")
                .queryParam("query", text);
        return builder;
    }

    // TODO buscar del listado la primera que sea pelicula (media_type == 'movie')
    @GetMapping("/fetch_data")
    public ResponseEntity<Result> First_movie(@RequestParam String text) {

        UriComponentsBuilder builder = Api_movie(text);

        LinkedList<Result> lista_movie = new LinkedList<>();

        try {
            ResultResponse response = restTemplate.getForObject(builder.toUriString(), ResultResponse.class);

            if (response.getResults().isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                for (Result r : response.getResults()) {
                    if (r.getMedia_type().equals("movie")) {
                        lista_movie.add(r);
                    }
                }
                return ResponseEntity.ok().body(lista_movie.getFirst());
            }

        } catch (RestClientException e) {
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}

// TODO obtener los details de esa movie -> "https://api.themoviedb.org/3/movie/{{ id }}"
// TODO devolver un objeto que tenga { id, name, overview }
