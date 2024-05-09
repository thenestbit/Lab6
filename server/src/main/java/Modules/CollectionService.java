package Modules;

import CollectionObject.*;
import Exceptions.*;
import java.util.*;
import java.util.stream.Collectors;

public class CollectionService {
//    protected static Long elementsCount = 0L;
    private Date initializationDate;
    protected static ArrayDeque<City> collection;
    protected static Scanner InputScanner;
    private CompareCities comparator;

    public CollectionService() {
        collection = new ArrayDeque<>();
        this.initializationDate = new Date();
        comparator = new CompareCities();
    }

    private class CompareCities implements Comparator<City>{

        @Override
        public int compare(City o1, City o2) {
            return (int) (o1.getCoordinates().getX() - o2.getCoordinates().getX());
        }

        @Override
        public Comparator<City> reversed() {
            return Comparator.super.reversed();
        }
    }

    protected record CityWithoutId (
            String name, Coordinates coordinates, Date creationDate,
            int area, Integer population, Float metersAboveSeaLevel,
            Long telephoneCode, long agglomeration,
            StandardOfLiving standardOfLiving, Human governor){}

    public ArrayDeque<City> addElement(CityModel source){
//        CityWithoutId source = createElement();
        UUID uuid = UUID.randomUUID();
        long mostSignificantBits = uuid.getMostSignificantBits();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        long newId = Math.abs(mostSignificantBits ^ leastSignificantBits);
        City newElement = new City(
                newId,
                source.getName(),
                source.getCoordinates(),
                new Date(),
                source.getArea(),
                source.getPopulation(),
                source.getMetersAboveSeaLevel(),
                source.getTelephoneCode(),
                source.getAgglomeration(),
                source.getStandardOfLiving(),
                source.getGovernor()
        );

        collection.addLast(newElement);
        return sortByCoords(collection);
    }

    public String info(){
        return "Тип коллекции: " + collection.getClass() + "\n"
                + "Дата создания: " + initializationDate + "\n"
                + "Количество элементов: " + collection.size();
    }

    public ArrayDeque<City> show(){
        return sortByCoords(collection);
    }

    public ArrayDeque<City> update(long current_id, CityModel source) throws NonExistingElementException{
        if (!collection.contains(getElementById(collection, (int) current_id))){
            System.out.println("Element doesn't exist");
        }
        for (City city:collection) {
            if (current_id == city.getId()){
                collection.remove(city);
                City newElement = new City(
                        current_id,
                        source.getName(),
                        source.getCoordinates(),
                        new Date(),
                        source.getArea(),
                        source.getPopulation(),
                        source.getMetersAboveSeaLevel(),
                        source.getTelephoneCode(),
                        source.getAgglomeration(),
                        source.getStandardOfLiving(),
                        source.getGovernor()
                );

                collection.addLast(newElement);
                break;

            }
        }
        return sortByCoords(collection);
    }

    public ArrayDeque<City> removeById(long id) throws NonExistingElementException {
        if (!collection.removeIf(city -> city.getId() == id)){
            throw new NonExistingElementException("Элемента с таким id не существует");
        }
        return sortByCoords(collection);
    }

    public ArrayDeque<City> clear(){
        collection.clear();
        return sortByCoords(collection);
    }

    public ArrayDeque<City> removeLower(long startId) throws NonExistingElementException {
        long endId = collection.size();
        if (startId < endId){
            throw new NonExistingElementException("Элемента с таким id не существует");
        }

        collection.removeIf(vehicle -> vehicle.getId() <= startId);
        return sortByCoords(collection);
    }

    public ArrayDeque<City> filterByStandardOfLiving(String standard) throws NonExistingElementException {
        var filteredCollection = collection.stream().filter(city -> city.getStandardOfLiving().equals(StandardOfLiving.valueOf(standard))).collect(Collectors.toCollection(ArrayDeque::new));
        if (collection.isEmpty()){
            throw new NonExistingElementException("Элементов с таким уровнем жизни не существует");
        }
        return sortByCoords(collection);
    }

    public long groupCountingByArea(int area) {
        return collection.stream().filter(city -> city.getArea() == area).count();
    }

    public ArrayDeque<City> addIfMin(CityModel source) {

        UUID uuid = UUID.randomUUID();
        long mostSignificantBits = uuid.getMostSignificantBits();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        long newId = Math.abs(mostSignificantBits ^ leastSignificantBits);
        City newCity = new City(
                newId,
                source.getName(),
                source.getCoordinates(),
                new Date(),
                source.getArea(),
                source.getPopulation(),
                source.getMetersAboveSeaLevel(),
                source.getTelephoneCode(),
                source.getAgglomeration(),
                source.getStandardOfLiving(),
                source.getGovernor()
        );

        if (collection.isEmpty() || newCity.getPopulation() < Collections.min(collection, Comparator.comparing(City::getPopulation)).getPopulation()) {
            collection.add(newCity);
            System.out.println("City added successfully.");
        } else {
            System.out.println("City was not added as its population is not minimal.");
        }
        return sortByCoords(collection);
    }

    public City minByCreationDate() {
//        if (collection.isEmpty()) {
//            System.out.println("The collection is empty. No objects to display.");
//            return;
//        }
//
//        City minByCreationDateCity = Collections.min(collection, Comparator.comparing(City::getCreationDate));
//
//        System.out.println("Object with the minimum creation date:" + "\n" + minByCreationDateCity.toString());

        return collection.stream().min(Comparator.comparing(City::getCreationDate)).orElseThrow(NoSuchElementException::new);
    }


    public City getElementById(ArrayDeque collection, int id) {
        List<City> c = collection.stream().toList();
        for (City city : c) {
            if (city.getId() == id) {
                return city;
            }
        }
        return null;
    }

    private ArrayDeque<City> sortByCoords(ArrayDeque<City> collection){
        return collection.stream().sorted(comparator).collect(Collectors.toCollection(ArrayDeque::new));
    }

}