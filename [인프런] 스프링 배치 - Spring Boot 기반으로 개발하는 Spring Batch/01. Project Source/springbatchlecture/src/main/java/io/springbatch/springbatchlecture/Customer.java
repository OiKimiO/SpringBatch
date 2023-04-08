package io.springbatch.springbatchlecture;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Data
@Entity
class Customer {

    private Long id;
    private String firstName;
    private String lastName;
    private String birthDate;

    public Customer(Long id, String firstName, String lastName, String birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public Customer() {
    }
}
