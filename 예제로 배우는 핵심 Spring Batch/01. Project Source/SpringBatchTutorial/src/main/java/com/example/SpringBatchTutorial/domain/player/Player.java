package com.example.SpringBatchTutorial.domain.player;

import java.io.Serializable;

import lombok.Data;

@Data
public class Player implements Serializable {

    private String ID;
    private String lastName;
    private String firstName;
    private String position;
    private int birthYear;
    private int debutYear;

}