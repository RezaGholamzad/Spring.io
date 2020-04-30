package io.spring.corsRESTfulWebService.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Greeting {

    private final long id;
    private final String content;

    public Greeting() {
        this.id = -1;
        this.content = "";
    }


}
