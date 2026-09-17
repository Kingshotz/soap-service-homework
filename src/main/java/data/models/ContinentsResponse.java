package data.models;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "ArrayOftContinent")
public class ContinentsResponse {

    private List<Continent> continents;

    public ContinentsResponse() {
    }

    @XmlElement(name = "tContinent")
    public List<Continent> getContinents() {
        return continents;
    }

    public void setContinents(List<Continent> continents) {
        this.continents = continents;
    }
}
