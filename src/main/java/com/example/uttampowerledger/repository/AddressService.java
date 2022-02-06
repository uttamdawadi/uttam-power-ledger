package com.example.uttampowerledger.repository;

import com.example.uttampowerledger.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public interface AddressService extends JpaRepository<Address, Long> {
    List<Address> findByPostcodeContaining(int postcode);

    List<Address>findAllByPostcodeBetween(int postcode1, int postcode2);

}

    //    public List<Address> getSuburbWithRangeOfPostcode(int postcode1, int postcode2) {
    //        return IntStream.iterate(postcode1, i -> i + 1)
    //                .limit(postcode2)
    //                .boxed()
    //                .collect(Collectors.toList());
    //    }