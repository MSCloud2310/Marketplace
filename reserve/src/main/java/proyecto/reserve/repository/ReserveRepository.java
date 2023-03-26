package proyecto.reserve.repository;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import proyecto.reserve.entities.Reserve;

import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class ReserveRepository {
    private static final Map<Integer, Reserve> reserves = new HashMap<>();

    @PostConstruct
    public void initData() throws DatatypeConfigurationException {
        GregorianCalendar calendar = new GregorianCalendar();

        // convert the GregorianCalendar to an XMLGregorianCalendar
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        XMLGregorianCalendar xmlGregorianCalendar = datatypeFactory.newXMLGregorianCalendar(calendar);
        xmlGregorianCalendar.setYear(2023);
        xmlGregorianCalendar.setMonth(3);
        xmlGregorianCalendar.setDay(25);
        xmlGregorianCalendar.setHour(12);
        xmlGregorianCalendar.setMinute(30);
        xmlGregorianCalendar.setSecond(0);
        Reserve re = new Reserve();
        re.setId(1);
        re.setIdService(1);
        re.setUserID(1);
        re.setFinishDate(xmlGregorianCalendar);
        re.setStartDate(xmlGregorianCalendar);
        re.setHour(xmlGregorianCalendar);
        reserves.put(re.getId(), re);


    }

    public Reserve findReserveById(int id) {
        return reserves.get(id);
    }
}
