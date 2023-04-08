package io.springbatch.springbatchlecture;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Address {

    private Long id;
    private String location;
    private Customer customer;
}
