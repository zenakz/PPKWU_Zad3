package com.ppkwu.zad3;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class Controller {


    @GetMapping(value = "/calendar")
    public ResponseEntity<Resource> downloadFile(Integer year, Integer month) throws IOException {
        if(year<0||month<0||month>12)throw new RuntimeException("wrong parameter values.");
        String fixedMonth = month.toString();
        if(fixedMonth.length()>1)fixedMonth = "0"+month;
        String calendarUrl="http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok="+year+"&miesiac="+fixedMonth+"&lang=1";

        Document doc = Jsoup.connect(calendarUrl).get();
        Elements elements = doc.select("td");


        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//Zientak//iCal4j 1.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        FileOutputStream fout = new FileOutputStream("calendar.ics");

        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(calendar, fout);
        Resource resource = new ClassPathResource("/mycalendar.ics");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
