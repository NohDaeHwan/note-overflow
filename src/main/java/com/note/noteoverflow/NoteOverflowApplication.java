package com.note.noteoverflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class NoteOverflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteOverflowApplication.class, args);
    }

}
