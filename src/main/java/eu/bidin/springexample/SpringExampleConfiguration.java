package eu.bidin.springexample;

import eu.bidin.springexample.persistence.MyHashtable;
import eu.bidin.springexample.validators.StudentValidator;
import eu.bidin.springexample.utilities.UuidParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class SpringExampleConfiguration {

    @Bean
    @Scope("singleton")
    public MyHashtable myHashtable() {
        return new MyHashtable();
    }

    @Bean
    @Scope("singleton")
    public UuidParser uuidParser() {
        return new UuidParser();
    }

    @Bean
    @Scope("singleton")
    public StudentValidator studentValidator() {
        return new StudentValidator();
    }
}