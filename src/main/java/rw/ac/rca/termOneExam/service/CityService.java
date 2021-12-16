package rw.ac.rca.termOneExam.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;

@Service
public class CityService {

	@Autowired
	private ICityRepository cityRepository;
	
	public Optional<City> getById(long id) {

		City city=	cityRepository.findById(id).get();
        city.setId(city.getId());
		city.setName(city.getName());
		city.setWeather(city.getWeather());
		city.setFahrenheit((city.getWeather()*1.8)+32);
		city= cityRepository.save(city);
		return  cityRepository.findById(id);

	}

	public List<City> getAll() {
		ArrayList<City> list= (ArrayList<City>) cityRepository.findAll();
		int i = 0;
		while (i < list.size()) {

			list.get(i).setName(list.get(i).getName());
			list.get(i).setWeather(list.get(i).getWeather());
			list.get(i).setFahrenheit((list.get(i).getWeather()*1.8)+32);
			cityRepository.save(list.get(i));
			i++;
		}
	return list;

	}

	public boolean existsByName(String name) {
		
		return cityRepository.existsByName(name);
	}

	public City save(CreateCityDTO dto) {
		City city =  new City(dto.getName(), dto.getWeather());
//         city.setId(105);
		System.out.println("createing city");
		return cityRepository.save(city);
	}
	public City update(long id,City dto) {
		City city1 =cityRepository.getById(id);
       city1.setName(dto.getName());
	   city1.setWeather(dto.getWeather());
	   city1.setFahrenheit((dto.getWeather()*(9/5))+32);
	   return cityRepository.save(city1);
	}

}
