package io.spring.batchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    private static final Logger LOG = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public Person process(final Person item) throws Exception {
        final String firstName = item.getFirstName().toUpperCase();
        final String lastName = item.getLastName().toUpperCase();
        final Person transformedPerson = new Person(firstName, lastName);
        LOG.info("Converting (" + item + ") into (" + transformedPerson + ")");
        return transformedPerson;
    }
}
