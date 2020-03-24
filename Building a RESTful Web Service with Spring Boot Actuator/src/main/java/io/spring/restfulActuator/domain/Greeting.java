package io.spring.restfulActuator.domain;

import lombok.Data;

@Data
public class Greeting {

    private final Long id;
    private final String content;
}
