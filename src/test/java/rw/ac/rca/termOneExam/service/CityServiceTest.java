package rw.ac.rca.termOneExam.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {
    @Mock
    private ICityRepository cityRepositoryMock;

    @InjectMocks
    private CityService cityService;
    @Test
    public void getAll(){
        when(cityRepositoryMock.findAll())
                .thenReturn(Arrays.asList(new City(101,"Kigali",24,75.2),
                        new City(102,"Musanze",18,64.4),new City(103,"Rubavu",20,68),new City(104,"Nyagatare",28,82.4)));

        assertEquals(4,cityService.getAll().size());
        assertEquals("Kigali",cityService.getAll().get(0).getName());
    }
    @Test
    public void getById_Success(){
        City city=new City(102,"Musanze",18,64.4);
        when(cityRepositoryMock.findById(city.getId())).thenReturn(Optional.of(city));
        assertEquals(102,cityService.getById(city.getId()).get().getId());
        assertEquals(64.4,cityService.getById(city.getId()).get().getFahrenheit());
    }
//    @Test
//    public void getById_notfound() {
//        when(cityRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
//
//        assertTrue(null,cityService.getById(108).isPresent());
//    }
    @Test
    public void save_Success(){
        when(cityRepositoryMock.save(ArgumentMatchers.any(City.class))).thenReturn(new City("Gakenke", 23));

        CreateCityDTO dto = new CreateCityDTO();
        dto.setName("Gakenke");
        dto.setWeather(23);

        assertEquals("Gakenke", cityService.save(dto).getName());
        assertEquals(23, cityService.save(dto).getWeather());

    }
    @Test
    public void existsByName_test() {
        when(cityRepositoryMock.existsByName(anyString())).thenReturn(true);

        assertTrue(cityService.existsByName("Musanze"));
    }


}
