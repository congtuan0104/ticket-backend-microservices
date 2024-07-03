package com.ticket.event.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.startup.RealmRuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.ticket.event.entities.City;
import com.ticket.event.repositories.CityRepository;

@RestController
@RequestMapping("/api")
public class CityController {
    @Autowired
    private CityRepository cityRepository;

    @GetMapping(value = "/city")
    public ResponseEntity<List<City>> getAllCity()
    {
        List<City> cities = new ArrayList<City>();
        try {
            cities = cityRepository.findAll();
            return new ResponseEntity<>(cities, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/city/{id}")
    public ResponseEntity<City> getCityById(
        @PathVariable("id") String cityId
        )
    {
        Optional<City> cityData = cityRepository.findById(cityId);
        if (cityData.isPresent())
        {
            return new ResponseEntity<>(cityData.get(), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "city/cityName")
    public ResponseEntity<List<City>> getCityByCityName(
        @RequestParam(name = "cityName") String cityName
        )
    {
        List<City> cities = new ArrayList<City>();
        try {
            cities = cityRepository.findByCityNameLike(cityName);
            if (cities.isEmpty())
            {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            else
            {
                return new ResponseEntity<>(cities, HttpStatus.OK);
            }
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/city")
    public ResponseEntity<City> createCity(
        @RequestBody City city
    )
    {
        try {
            City _city = cityRepository.save(city);
            return new ResponseEntity<>(_city, HttpStatus.CREATED);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/city")
    public ResponseEntity<City> updateCityById(
        @RequestBody City inputCity
    )
    {
        Optional<City> cityData = cityRepository.findById(inputCity.getCityId().toString());
        if (cityData.isPresent())
        {
            City _city = cityData.get();
            _city.setCityName(inputCity.getCityName());

            return new ResponseEntity<>(cityRepository.save(_city), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping(value = "/city")
    public ResponseEntity<HttpStatus> deleteCityById(
        @RequestParam(name = "cityId") String cityId
    )
    {
        if (cityRepository.findById(cityId).isPresent())
        {
            try {
                cityRepository.deleteById(cityId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                // TODO: handle exception
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }
}
