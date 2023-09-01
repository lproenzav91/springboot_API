package com.testandres.repaso.controller;

import com.testandres.repaso.controller.model.ResultResponse;
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

    @GetMapping("/fetch_data")
    public ResponseEntity<ResultResponse> fetchDataFromOtherApi(@RequestParam String text) {
        String apiUrl = "https://api.themoviedb.org/3/search/multi";  // URL de la otra API

        //  'api_key': '3e56846ee7cfb0b7d870484a9f66218c'
        // 'query'
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("api_key", "3e56846ee7cfb0b7d870484a9f66218c")
                .queryParam("query", text);
        try {
            ResultResponse response = restTemplate.getForObject(builder.toUriString(), ResultResponse.class);
            
            if (response.getResults().isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            
            // TODO buscar del listado la primera que sea pelicula (media_type == 'movie')
            // TODO obtener los details de esa movie -> "https://api.themoviedb.org/3/movie/{{ id }}"
            // TODO devolver un objeto que tenga { id, name, overview }
            
            return ResponseEntity.ok().body(response);
        } catch (RestClientException e) {
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
