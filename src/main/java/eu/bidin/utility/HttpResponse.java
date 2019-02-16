package eu.bidin.utility;

import eu.bidin.springexample.controllers.StudentController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

public class HttpResponse {

    public static ResponseEntity created(Class controller, String action, String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(MvcUriComponentsBuilder
                .fromMethodName(controller, action, id)
                .buildAndExpand(id)
                .toUri());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
}
