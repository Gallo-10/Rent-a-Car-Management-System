package BackendCode;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author @AbdullahShahid01
 */
public class Booking implements Serializable {

    private int ID;
    private Customer customer;
    private Car car;
    private TimeInterval timeInterval;

    public Booking() {
    }

    public Booking(int ID, Customer customer, Car car, TimeInterval timeInterval) {
        this.ID = ID;
        this.customer = customer;
        this.car = car;
        this.timeInterval = timeInterval;
    }
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(TimeInterval timeInterval) {
        this.timeInterval = timeInterval;
    }
    
    public long getRentTime(){
        return timeInterval.getRentTime();
    }
    
    public long getReturnTime(){
        return timeInterval.getReturnTime();
    }
    
    public TimeInterval setReturnTime(long x){
        timeInterval.setReturnTime(x);
        return null;
    };

    @Override
    public String toString() {
        return "Booking{" + "ID=" + ID + ", \ncustomer=" + customer.toString() + ", \ncar=" + car.toString() + ", \ntimeInterval=" + timeInterval.toString() + '}' + "\n";
    }

    public void add() {
        ArrayList<Booking> bookings = Booking.view();
        if (bookings.isEmpty()) {
            this.ID = 1;
        } else {
            this.ID = bookings.get(bookings.size() - 1).ID + 1; // Auto ID ...
        }
        bookings.add(this);
        writeFile(bookings);
    }

    public void update() {
        ArrayList<Booking> bookings = Booking.view();

        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).ID == ID) {
                bookings.set(i, this);
            }
        }

        writeFile(bookings);
    }

    public void remove() {
        ArrayList<Booking> bookings = Booking.view();

        bookings.removeIf(b -> b.ID == this.ID);

        writeFile(bookings);
    }

    public int calculateBill() {
        long totalTime = timeInterval.getTotalHours();

        int rentPerHour = this.getCar().getRentPerHour();
        return totalTime != 0 ? (int) (rentPerHour * totalTime) : rentPerHour;
    }

    private static void writeFile(ArrayList<Booking> bookings) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Booking.ser"))) {
            for (Booking booking : bookings) {
                outputStream.writeObject(booking);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public static ArrayList<Booking> searchByCustomerID(int customerID) {
        return searchBookings(booking -> booking.customer.getID() == customerID);
    }

    public static ArrayList<Booking> searchByCarRegNo(String carRegNo) {
        return searchBookings(booking -> booking.car.getRegNo().equalsIgnoreCase(carRegNo));
    }

    public static ArrayList<Booking> searchByCarID(int carID) {
        return searchBookings(booking -> booking.car.getID() == carID);
    }

    public static ArrayList<Booking> view() {
        return searchBookings(null);
    }

    private static ArrayList<Booking> searchBookings(BookingPredicate predicate) {
        ArrayList<Booking> bookingList = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("Booking.ser"))) {
            boolean EOF = false;
            while (!EOF) {
                try {
                    Booking booking = (Booking) inputStream.readObject();
                    if (predicate == null || predicate.test(booking)) {
                        bookingList.add(booking);
                    }
                } catch (ClassNotFoundException | EOFException e) {
                    EOF = true;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return bookingList;
    }

    public static ArrayList<Car> getBookedCars() {
        ArrayList<Car> bookedCars = new ArrayList<>();
        ArrayList<Booking> bookings = Booking.view();
        for (Booking booking : bookings) {
            if (booking.timeInterval.getReturnTime() == 0) {
                bookedCars.add(booking.getCar());
            }
        }
        return bookedCars;
    }

    public static ArrayList<Car> getUnbookedCars() {
        ArrayList<Car> allCars = Car.view();
        ArrayList<Car> bookedCars = Booking.getBookedCars();
        allCars.removeAll(bookedCars);
        return allCars;
    }

    @FunctionalInterface
    private interface BookingPredicate {
        boolean test(Booking booking);
    }
}
