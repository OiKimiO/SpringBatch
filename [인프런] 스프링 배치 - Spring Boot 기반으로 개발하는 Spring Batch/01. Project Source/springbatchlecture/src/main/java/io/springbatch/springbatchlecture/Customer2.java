package io.springbatch.springbatchlecture;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
class Customer2 {

    private Long id;
    private String firstName;
    private String lastName;
    private String birthDate;

    public Customer2(Long id, String firstName, String lastName, String birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

}
