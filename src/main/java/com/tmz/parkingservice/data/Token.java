package com.tmz.parkingservice.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.nio.charset.Charset;
import java.util.Random;

@Entity
@Table(name="token")
public class Token {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JsonProperty("value")
    private String value;

    @JoinColumn(name="wid")
    private int wardenId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public int getWardenId() {
        return wardenId;
    }

    public void setWardenId(int wardenId) {
        this.wardenId = wardenId;
    }

    public String generateToken() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 4;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        this.value = generatedString;
        return generatedString;
    }


}
