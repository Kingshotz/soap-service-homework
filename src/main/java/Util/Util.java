package Util;

import com.example.springboot.soap.interfaces.EmployeeInfo;
import com.example.springboot.soap.interfaces.ObjectFactory;
import com.github.javafaker.Faker;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

public class Util {
    private static final ObjectFactory objectFactory = new ObjectFactory();
    private static final Faker faker = new Faker();


    public static EmployeeInfo createEmployeeInfo() {
        EmployeeInfo employeeInfo = objectFactory.createEmployeeInfo();
        employeeInfo.setEmployeeId(faker.number().numberBetween(1,30));
        employeeInfo.setName(faker.name().fullName());
        employeeInfo.setDepartment(faker.job().title());
        employeeInfo.setPhone(faker.phoneNumber().cellPhone());
        employeeInfo.setAddress(faker.address().fullAddress());
        employeeInfo.setSalary(BigDecimal.valueOf(faker.number().numberBetween(1000, 10000)));
        employeeInfo.setEmail(faker.internet().emailAddress());
        employeeInfo.setBirthDate(generateBirthDate());


        return employeeInfo;
    }
    private static XMLGregorianCalendar generateBirthDate(){
        LocalDate birthDate = faker.date()
                .birthday(18, 65)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        try {
            XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(birthDate.toString());
            return xmlDate;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
