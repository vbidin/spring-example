package eu.bidin.springexample;

import eu.bidin.springexample.entities.Student;
import eu.bidin.utility.MyHashtable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import java.util.UUID;

@Configuration
public class ApplicationConfiguration {

    @Bean
    @Scope("singleton")
    public MyHashtable<UUID, Student> database() {
        return new MyHashtable();
    }
}