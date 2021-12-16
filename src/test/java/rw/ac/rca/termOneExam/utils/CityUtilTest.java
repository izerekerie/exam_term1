package rw.ac.rca.termOneExam.utils;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.repository.ICityRepository;
import rw.ac.rca.termOneExam.service.CityService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CityUtilTest {
    @Autowired
    private ICityRepository cityRepository;

    @Test
    public void NoWeather_greater40() {


        ArrayList<City> list= (ArrayList<City>) cityRepository.findAll();
		int i = 0;
		while (i < list.size()) {

		assertTrue(list.get(i).getWeather()<40);
        i++;
		}

    }
    @Test
    public void NoWeather_less10() {


        ArrayList<City> list= (ArrayList<City>) cityRepository.findAll();
        int i = 0;
        while (i < list.size()) {

            assertTrue(list.get(i).getWeather()>10);
            i++;
        }

    }
    @Test
    public void Cities_Kigali_Musanze() {
         ArrayList<String> cities=new ArrayList<>();
        ArrayList<City> list= (ArrayList<City>) cityRepository.findAll();
        int i = 0;
        while (i < list.size()) {

           cities.add(list.get(i).getName());
            i++;
        }
        assertTrue(cities.contains("Kigali"));
         assertTrue(cities.contains("Musanze"));
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class TestingSpy {

        @Spy
        private ICityRepository cityRepositoryBySpy;

        @InjectMocks
        private CityService cityServiceMock;

        @Test
        public void testSpying() {
            when(cityRepositoryBySpy.findAll()).thenReturn(Arrays.asList(new City("Burera", 18), new City("Huye", 26),new City("Gakenke", 22)));

            List<City> cities = cityServiceMock.getAll();

            assertEquals(3, cities.size());
        }

    }


    @RunWith(MockitoJUnitRunner.class)
    public static class TestingMock {

        @Mock
        private ICityRepository cityRepositoryByMock;

        @InjectMocks
        private CityService cityServiceMock;

        @Test
        public void testMocking() {
            when(cityRepositoryByMock.findAll()).thenReturn(Arrays.asList(new City("Burera", 18), new City("Huye", 26),new City("Gakenke", 22)));

            List<City> cities = cityServiceMock.getAll();

            assertEquals(3, cities.size());
        }

    }

}
