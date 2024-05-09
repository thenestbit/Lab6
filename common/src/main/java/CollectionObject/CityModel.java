package CollectionObject;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class CityModel implements Serializable {

    private String name;
    private Coordinates coordinates;
    private int area;
    private Integer population;
    private Float metersAboveSeaLevel;
    private Long telephoneCode;
    private long agglomeration;
    private StandardOfLiving standardOfLiving;
    private Human governor;

    public CityModel(String name, Coordinates coordinates, int area, Integer population, Float metersAboveSeaLevel, Long telephoneCode, long agglomeration, StandardOfLiving standardOfLiving, Human governor) {
        this.name = name;
        this.coordinates = coordinates;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.telephoneCode = telephoneCode;
        this.agglomeration = agglomeration;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Float getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public void setMetersAboveSeaLevel(Float metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public Long getTelephoneCode() {
        return telephoneCode;
    }

    public void setTelephoneCode(Long telephoneCode) {
        this.telephoneCode = telephoneCode;
    }

    public long getAgglomeration() {
        return agglomeration;
    }

    public void setAgglomeration(Long agglomeration) {
        this.agglomeration = agglomeration;
    }

    public StandardOfLiving getStandardOfLiving() {
        return standardOfLiving;
    }

    public void setStandardOfLiving(StandardOfLiving standardOfLiving) {
        this.standardOfLiving = standardOfLiving;
    }

    public Human getGovernor() {
        return governor;
    }

    public void setGovernor(Human governor) {
        this.governor = governor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityModel cityModel = (CityModel) o;
        return area == cityModel.area && agglomeration == cityModel.agglomeration && Objects.equals(name, cityModel.name) && Objects.equals(coordinates, cityModel.coordinates) && Objects.equals(population, cityModel.population) && Objects.equals(metersAboveSeaLevel, cityModel.metersAboveSeaLevel) && Objects.equals(telephoneCode, cityModel.telephoneCode) && standardOfLiving == cityModel.standardOfLiving && Objects.equals(governor, cityModel.governor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, area, population, metersAboveSeaLevel, telephoneCode, agglomeration, standardOfLiving, governor);
    }

    @Override
    public String toString() {
        if (this.governor == null) {
            return "City{" +
                    ", name='" + name + '\'' +
                    ", coordinates=" + coordinates +
                    ", area=" + area +
                    ", population=" + population +
                    ", metersAboveSeaLevel=" + metersAboveSeaLevel +
                    ", telephoneCode=" + telephoneCode +
                    ", agglomeration=" + agglomeration +
                    ", standardOfLiving=" + standardOfLiving +
                    ", governor=" + null +
                    '}';
        }
        return "City{" +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", area=" + area +
                ", population=" + population +
                ", metersAboveSeaLevel=" + metersAboveSeaLevel +
                ", telephoneCode=" + telephoneCode +
                ", agglomeration=" + agglomeration +
                ", standardOfLiving=" + standardOfLiving +
                ", governor=" + governor.getName() +
                '}';
    }

}
