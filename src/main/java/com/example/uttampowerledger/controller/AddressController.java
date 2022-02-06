package com.example.uttampowerledger.controller;


import com.example.uttampowerledger.model.Address;
import com.example.uttampowerledger.repository.AddressService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AddressService addressService;


    @GetMapping("/address")
    public ResponseEntity<List<Address>> getAllAddress(@RequestParam(required = false) String postcode) {
        try {
            List<Address> addresses = new ArrayList<Address>();
            if (postcode == null)
                addressService.findAll().forEach(addresses::add);
            else {
                addressService.findByPostcodeContaining(Integer.parseInt(postcode)).forEach(addresses::add);
            }
            if (addresses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(addresses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/address/postcode")
    public ResponseEntity<Object> getAllAddressPostcodeRange(@RequestParam(required = false) String from, String to) {
        try {
            List<JSONObject> entities = new ArrayList<JSONObject>();
            List<Address> addresses = new ArrayList<Address>();

            if(to == null || from == null)
                return new ResponseEntity<>("Postcode from or to can't be empty",HttpStatus.BAD_REQUEST);

            addressService.findAllByPostcodeBetween(Integer.parseInt(from), Integer.parseInt(to)).forEach(addresses::add);

            if (addresses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            //sorted the data alphabetically

            String suburbName = "";

            List<Address> sortedList = addresses.stream()
                    .sorted(Comparator.comparing(Address::getSuburb))
                    .collect(Collectors.toList());
            //collect the suburb name for counting
            for (int i = 0; i < sortedList.size(); i++) {
                suburbName = suburbName + sortedList.get(i).getSuburb();
            }

            JSONObject entity = new JSONObject();
            entity.put("Result", sortedList);
            entity.put("Suburb", suburbName);
            entity.put("Total_Suburb_Char_Count", suburbName.chars().count());
            entities.add(entity);
            return new ResponseEntity<>(entities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/address")
    public ResponseEntity<List<Address>> createTutorial(@RequestBody List<Address> address) {
        try {
            List<Address> _addressList = new ArrayList<>();
            for (int i = 0; i < address.size(); i++) {
                Address _address = addressService.save(new Address(address.get(i).getPostcode(), address.get(i).getSuburb()));
                _addressList.add(_address);
            }
            return new ResponseEntity<List<Address>>(_addressList, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
