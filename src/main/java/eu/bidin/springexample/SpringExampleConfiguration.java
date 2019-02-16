package eu.bidin.springexample;

import eu.bidin.springexample.entities.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configuration {

    @Bean
    public Student student() {
        return new Student(null, "peewee", 3);
    }
}