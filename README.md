# PPKWU_Zad3

# Endpoints:

## /calendar(int year, int month) (GET)

### returns
An ics file calendar for year and month given in parameters. If any of the parameters are incorrect, or there are no events in the given month, an exception is thrown.

### Example
http://localhost:8080/calendar?year=2020&month=3

Downloads an ics file for march 2020 of this content:
```

BEGIN:VCALENDAR
PRODID:-//Zientak//iCal4j 1.0//EN
VERSION:2.0
CALSCALE:GREGORIAN
BEGIN:VEVENT
DTSTAMP:20201201T142132Z
DTSTART;VALUE=DATE:20200324
SUMMARY:First Step to Fields Medal
UID:testUid
URL:http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=2
 020&miesiac=02&lang=1
END:VEVENT
BEGIN:VEVENT
DTSTAMP:20201201T142132Z
DTSTART;VALUE=DATE:20200326
SUMMARY:First Step to Success
UID:testUid
URL:http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=2
 020&miesiac=02&lang=1
END:VEVENT
BEGIN:VEVENT
DTSTAMP:20201201T142132Z
DTSTART;VALUE=DATE:20200327
SUMMARY:Piękne doświadczenie\, Fascynujące Wyjaśnienie
UID:testUid
URL:http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=2
 020&miesiac=02&lang=1
END:VEVENT
BEGIN:VEVENT
DTSTAMP:20201201T142132Z
DTSTART;VALUE=DATE:20200328
SUMMARY:First Step to Nobel Prize
UID:testUid
URL:http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=2
 020&miesiac=02&lang=1
END:VEVENT
END:VCALENDAR
```
