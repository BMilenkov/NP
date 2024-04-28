package K2.Car;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


class Car {
    private final String manifacturer;
    private final String model;
    private final int price;
    private final float power;

    public Car(String manifacturer, String model, int price, float power) {
        this.manifacturer = manifacturer;
        this.model = model;
        this.price = price;
        this.power = power;
    }

    public String getManifacturer() {
        return manifacturer;
    }

    public int getPrice() {
        return price;
    }

    public String getModel() {
        return model;
    }

    public float getPower() {
        return power;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%dKW) %d", manifacturer, model, (int) power, price);
    }
}

class CarCollection {
    private final List<Car> cars;

    Comparator<Car> comparator = Comparator.comparing(Car::getPrice).thenComparing(Car::getPower);
    public CarCollection() {
        this.cars = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void sortByPrice(boolean ascending) {
        if (!ascending)
            cars.sort(comparator.reversed());
        else
            cars.sort(comparator);
    }
    public List<Car> filterByManufacturer(String manufacturer) {
        return cars.stream().filter(car -> car.getManifacturer().equalsIgnoreCase(manufacturer)).sorted(Comparator.comparing(Car::getModel)).collect(Collectors.toList());
    }

    public List<Car> getList() {
        return this.cars;
    }
}

public class CarTest {
    public static void main(String[] args) {
        CarCollection carCollection = new CarCollection();
        String manufacturer = fillCollection(carCollection);
        carCollection.sortByPrice(true);
        System.out.println("=== Sorted By Price ASC ===");
        print(carCollection.getList());
        carCollection.sortByPrice(false);
        System.out.println("=== Sorted By Price DESC ===");
        print(carCollection.getList());
        System.out.printf("=== Filtered By Manufacturer: %s ===\n", manufacturer);
        List<Car> result = carCollection.filterByManufacturer(manufacturer);
        print(result);
    }

    static void print(List<Car> cars) {
        for (Car c : cars) {
            System.out.println(c);
        }
    }

    static String fillCollection(CarCollection cc) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            if (parts.length < 4) return parts[0];
            Car car = new Car(parts[0], parts[1], Integer.parseInt(parts[2]),
                    Float.parseFloat(parts[3]));
            cc.addCar(car);
        }
        scanner.close();
        return "";
    }
}
