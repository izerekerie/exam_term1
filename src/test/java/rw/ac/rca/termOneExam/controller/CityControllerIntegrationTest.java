package rw.ac.rca.termOneExam.controller;

import org.json.JSONException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.utils.APICustomResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAll_success() throws JSONException {
        String response = this.restTemplate.getForObject("/api/cities/all", String.class);;

        JSONAssert.assertEquals("[{\"id\":101,\"name\":\"Kigali\",\"weather\":24,\"fahrenheit\": 75.2},{\"id\":102,\"name\":\"Musanze\",\"weather\":18,\"fahrenheit\": 64.4},{\"id\":103,\"name\":\"Rubavu\",\"weather\":20,\"fahrenheit\": 68},{\"id\":104,\"name\":\"Nyagatare\",\"weather\":28,\"fahrenheit\": 82.4}]", response, true);
    }
    @Test
    public void getById_Success() throws JSONException {
        ResponseEntity<City> response = this.restTemplate.getForEntity("/api/cities/id/101", City.class);

        assertEquals(200, response.getStatusCodeValue());

        assertEquals("Kigali", response.getBody().getName());
        assertEquals(75.2,response.getBody().getFahrenheit());
    }

    @Test
    public void getById_404() {
        ResponseEntity<APICustomResponse> response = this.restTemplate.getForEntity("/api/cities/id/105", APICustomResponse.class);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("City not found with id", response.getBody().getMessage());
    }
    @Test
    public void saveCity_Success() {

        CreateCityDTO city=new CreateCityDTO("Gakenke",20);
        ResponseEntity<City> response = this.restTemplate.postForEntity("/api/cities/add", city, City.class);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Gakenke",response.getBody().getName());

    }

    @Test
    public void saveCity_404() {
        CreateCityDTO city=new CreateCityDTO("Musanze",20);
        ResponseEntity<APICustomResponse> response = this.restTemplate.postForEntity("/api/cities/add", city, APICustomResponse.class);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("City name "+city.getName()+" is registered already",response.getBody().getMessage());

    }
    @Test
    public void create_testNameAlreadyRegistered() {
        CreateCityDTO dto = new CreateCityDTO("Musanze",18);


        ResponseEntity<APICustomResponse> response = restTemplate.postForEntity("/api/cities/add", dto, APICustomResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("City name " + dto.getName() + " is registered already", response.getBody().getMessage());
    }
}



