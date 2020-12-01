package com.ppkwu.zad3;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.UidGenerator;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.text.ParseException;


@RestController
public class Controller {


    @GetMapping(value = "/calendar")
    public void downloadFile(Integer year, Integer month, HttpServletResponse response) throws IOException, ParseException, URISyntaxException {
        if(year<0||month<0||month>12)throw new RuntimeException("wrong parameter values.");
        String fixedMonth = month.toString();
        if(fixedMonth.length()<2)fixedMonth = "0"+month;
        String calendarUrl="http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok="+year+"&miesiac="+fixedMonth+"&lang=1";

        Document doc = Jsoup.connect(calendarUrl).get();
        Elements elements = doc.select("td");

        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//Zientak//iCal4j 1.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
        UidGenerator uidgen = () -> new Uid("testUid");
        for (Element element : elements) {
            if(element.attr("class").equals("active")) {
                VEvent vEvent = new VEvent(new Date(year.toString() + fixedMonth + element.child(0).text()), element.getElementsByTag("p").text());
                vEvent.getProperties().add(uidgen.generateUid());
                Url url = new Url();
                url.setValue(calendarUrl);
                vEvent.getProperties().add(url);
                calendar.getComponents().add(vEvent);
            }
        }

        FileOutputStream fout = new FileOutputStream("calendar.ics");

        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(calendar, fout);

        InputStream inputStream = new FileInputStream(new File("calendar.ics"));
        IOUtils.copy(inputStream, response.getOutputStream());

    }
}
