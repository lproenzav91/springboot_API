package com.testandres.repaso.controller;

import com.testandres.repaso.model.Result;
import com.testandres.repaso.model.ResultResponse;
import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class PeliculaController {

    @Autowired
    private RestTemplate restTemplate;

    public UriComponentsBuilder Api_movie(String text) {
        String apiUrl = "https://api.themoviedb.org/3/search/multi";  // URL del API Movie.

        //  'api_key': '3e56846ee7cfb0b7d870484a9f66218c'
        // 'query'
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("api_key", "3e56846ee7cfb0b7d870484a9f66218c")
                .queryParam("query", text);
        return builder;
    }

    public UriComponentsBuilder movie_detail(int id) {
        String apiUrl = "https://api.themoviedb.org/3/movie/";  // URL del API Movie_detail.

        //  'api_key': '3e56846ee7cfb0b7d870484a9f66218c'
        // 'query'
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("api_key", "3e56846ee7cfb0b7d870484a9f66218c")
                .queryParam("movie_id", id);
        return builder;
    }

    // TODO buscar del listado la primera que sea pelicula (media_type == 'movie').
    @GetMapping("/first_movie")
    public ResponseEntity<Result> First_Movie(@RequestParam String text) {
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

    // TODO obtener los details de una movie -> "https://api.themoviedb.org/3/movie/{{ id }}".
    public UriComponentsBuilder Api_datail_movie(int id) {
        String apiUrl = "https://api.themoviedb.org/3/movie/" + id;  // URL del API Movie.

        //  'api_key': '3e56846ee7cfb0b7d870484a9f66218c'
        // 'query'
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("api_key", "3e56846ee7cfb0b7d870484a9f66218c");
        return builder;
    }

    @GetMapping("/movie_detail")
    public ResponseEntity<Result> Movie_Detail(@RequestParam int id) {
        UriComponentsBuilder builder = Api_datail_movie(id);
        try {
            Result response = restTemplate.getForObject(builder.toUriString(), Result.class);
            return ResponseEntity.ok().body(response);
        } catch (RestClientException e) {
            return ResponseEntity.internalServerError().build();
        }
        
    }

}


// TODO devolver un objeto que tenga { id, name, overview }.
