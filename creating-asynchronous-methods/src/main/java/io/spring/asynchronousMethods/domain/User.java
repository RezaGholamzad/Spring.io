package io.spring.asynchronousMethods.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
/*
    Spring uses the Jackson JSON library to convert GitHub’s JSON response into a User object.
    The @JsonIgnoreProperties annotation tells Spring to ignore any attributes not listed in the class.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String name;
    private String blog;

}
