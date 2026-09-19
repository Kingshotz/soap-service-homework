package db.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private Long employeeId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String department;
    private BigDecimal salary;
    private LocalDate birthDate;

}
