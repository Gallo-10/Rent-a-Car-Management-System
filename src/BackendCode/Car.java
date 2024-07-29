package BackendCode;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author @AbdullahShahid01
 */
public class Car implements Serializable {

    private int ID;
    private String Maker, Name, Colour, Type;
    int SeatingCapacity;
    String Model, Condition, RegNo;
    private int RentPerHour;
    private CarOwner carOwner;

    public Car() {
    }

    @FunctionalInterface
    public interface CarPredicate {

        boolean test(Car car);
    }

    public class CarID {

        private final int id;

        public CarID(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public Car(int ID, String Maker, String Name, String Colour, String Type, int SeatingCapacity, String Model, String Condition, String RegNo, int RentPerHour, CarOwner carOwner) {
        this.ID = ID;
        this.Maker = Maker;
        this.Name = Name;
        this.Colour = Colour;
        this.Type = Type;
        this.SeatingCapacity = SeatingCapacity;
        this.Model = Model;
        this.Condition = Condition;
        this.RegNo = RegNo;
        this.RentPerHour = RentPerHour;
        this.carOwner = carOwner;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMaker() {
        return Maker;
    }

    public void setMaker(String Maker) {
        this.Maker = Maker;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getColour() {
        return Colour;
    }

    public void setColour(String Colour) {
        this.Colour = Colour;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public int getSeatingCapacity() {
        return SeatingCapacity;
    }

    public void setSeatingCapacity(int SeatingCapacity) {
        this.SeatingCapacity = SeatingCapacity;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String Model) {
        this.Model = Model;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String Condition) {
        this.Condition = Condition;
    }

    public String getRegNo() {
        return RegNo;
    }

    public void setRegNo(String RegNo) {
        this.RegNo = RegNo;
    }

    public int getRentPerHour() {
        return RentPerHour;
    }

    public void setRentPerHour(int RentPerHour) {
        this.RentPerHour = RentPerHour;
    }

    public CarOwner getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(CarOwner carOwner) {
        this.carOwner = carOwner;
    }

    @Override
    public String toString() {
        return "Car_new{" + "ID=" + ID + ", Maker=" + Maker + ", Name=" + Name + ", Colour=" + Colour + ", \nType=" + Type + ", SeatingCapacity=" + SeatingCapacity + ", Model=" + Model + ", Condition=" + Condition + ", RegNo=" + RegNo + ", RentPerHour=" + RentPerHour + ", \ncarOwner=" + carOwner.toString() + '}' + "\n";
    }

    public static ArrayList<Car> view() {
        return searchCars(null);
    }
    
    public static ArrayList<Car> searchCars(CarPredicate predicate) {
    ArrayList<Car> carList = new ArrayList<>();
    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("Car.ser"))) {
        boolean EOF = false;
        while (!EOF) {
            try {
                Car car = (Car) inputStream.readObject();
                if (predicate == null || predicate.test(car)) {
                    carList.add(car);
                }
            } catch (ClassNotFoundException | EOFException e) {
                EOF = true;
            }
        }
    } catch (IOException e) {
        System.out.println(e);
    }
    return carList;
}


    public void Add() {
        ArrayList<Car> car = View();
        if (car.isEmpty()) {
            this.ID = 1;
        } else {
            this.ID = car.get(car.size() - 1).ID + 1;
        }
        car.add(this);
        try {
            writeToFile(car, "Car.ser");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void Update() {
        ArrayList<Car> car = View();
        for (int i = 0; i < car.size(); i++) {
            if (car.get(i).ID == ID) {
                car.set(i, this);
            }
        }
        try {
            writeToFile(car, "Car.ser");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void Remove() {
        ArrayList<Car> car = View();
        car.removeIf(c -> c.ID == ID);
        try {
            writeToFile(car, "Car.ser");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public static ArrayList<Car> SearchByName(String name) {
        ArrayList<Car> car = Car.View();
        ArrayList<Car> s = new ArrayList<>();
        for (int i = 0; i < car.size(); i++) {
            if (car.get(i).Name.equalsIgnoreCase(name)) {
                s.add(car.get(i));
            }
        }
        return s;
    }

    public static Car SearchByID(int id) {
        ArrayList<Car> car = Car.View();
        for (int i = 0; i < car.size(); i++) {
            if (car.get(i).ID == id) {
                return car.get(i);
            }
        }
        return null;
    }

    public static Car SearchByRegNo(String regNo) {
        ArrayList<Car> car = Car.View();
        for (int i = 0; i < car.size(); i++) {
            if (car.get(i).RegNo.equalsIgnoreCase(regNo)) {
                return car.get(i);
            }
        }
        return null;
    }

    public static ArrayList<Car> View() {
        ArrayList<Car> carList = new ArrayList<>(0);
        ObjectInputStream inputStream = null;
        try {
// open file for reading
            inputStream = new ObjectInputStream(new FileInputStream("Car.ser"));
            boolean EOF = false;
// Keep reading file until file ends
            while (!EOF) {
                try {
                    Car myObj = (Car) inputStream.readObject();
                    carList.add(myObj);
                } catch (ClassNotFoundException e) {
                    System.out.println(e);
                } catch (EOFException end) {
                    EOF = true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return carList;
    }

    public static boolean isNameValid(String Name) {
        boolean flag = false;
        for (int i = 0; i < Name.length(); i++) {
//            Name can contain white spaces
            if (Character.isLetter(Name.charAt(i)) | Character.isDigit(Name.charAt(i)) | Name.charAt(i) == ' ') {
                flag = true;
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }

    public static boolean isRegNoValid(String RegNo) {
        // reg no must contain letters followed by digits, both separated by '-' dash
        // EXAMPLE: ASD-2343
        String[] token = RegNo.split("-");
        if (token.length == 2) {
            for (int i = 0; i < token[0].length(); i++) {
                if (!Character.isLetter(token[0].charAt(i))) {
                    return false;
                }
            }
            for (int i = 0; i < token[1].length(); i++) {
                if (!Character.isDigit(token[1].charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean isRented() {
        ArrayList<Car> BookedCars = Booking.getBookedCars();
        for (int i = 0; i < BookedCars.size(); i++) {
            if (BookedCars.get(i).ID == this.ID) {
                return true;
            }
        }
        return false;
    }

    private void writeToFile(ArrayList<Car> carList, String fileName) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (Car car : carList) {
                outputStream.writeObject(car);
            }
        }
    }

    private ArrayList<Car> readFromFile(String fileName) throws IOException, ClassNotFoundException {
        ArrayList<Car> carList = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            while (true) {
                carList.add((Car) inputStream.readObject());
            }
        } catch (EOFException ignored) {
            // End of file reached
        }
        return carList;
    }

}
