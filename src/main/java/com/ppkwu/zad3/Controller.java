package com.ppkwu.zad3;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.io.*;

@RestController
public class Controller {


    @GetMapping(value = "/calendar")
    public ResponseEntity<Resource> downloadFile(int year, int month) throws FileNotFoundException {

        FileInputStream fin = new FileInputStream("mycalendar.ics");

        CalendarBuilder builder = new CalendarBuilder();

        try {
            Calendar calendar = builder.build(fin);
        } catch (IOException | ParserException e) {
            e.printStackTrace();
        }

        Resource resource = new ClassPathResource("mycalendar.ics");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
