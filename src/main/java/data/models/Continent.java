package data.models;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"code", "name"})
public class Continent {

    private String code;
    private String name;

    public Continent() {
    }

    @XmlElement(name = "sCode")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlElement(name = "sName")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}