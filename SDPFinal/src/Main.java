import java.util.Scanner;
import java.util.Random;

// Existing Hotel Booking System
interface HotelBookingSystem {
    void bookRoom(int roomNumber, double amount);
}

class HotelBooking implements HotelBookingSystem {
    @Override
    public void bookRoom(int roomNumber, double amount) {
        System.out.println("Room " + roomNumber + " booked in the hotel's system for $" + amount);
    }
}

// Third-Party Payment System
interface ThirdPartyPaymentSystem {
    void makePayment(double amount);
}

class PaymentSystem implements ThirdPartyPaymentSystem {
    @Override
    public void makePayment(double amount) {
        System.out.println("Payment of $" + amount + " processed in the third-party payment system.");
    }
}

// Adapter Pattern
class PaymentAdapter implements HotelBookingSystem {
    private ThirdPartyPaymentSystem paymentSystem;

    public PaymentAdapter(ThirdPartyPaymentSystem paymentSystem) {
        this.paymentSystem = paymentSystem;
    }

    @Override
    public void bookRoom(int roomNumber, double amount) {
        // Simulate payment before booking the room in the hotel system
        paymentSystem.makePayment(amount); // Make payment in the third-party system

        HotelBooking hotelBooking = new HotelBooking();
        hotelBooking.bookRoom(roomNumber, amount); // Book room in the hotel system
    }
}


// Strategy Pattern
interface PricingStrategy {
    double calculatePrice(double basePrice);
}

class StandardPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(double basePrice) {
        return basePrice; // Standard pricing
    }
}

class DiscountedPricingStrategy implements PricingStrategy {
    private double discountPercentage;

    public DiscountedPricingStrategy() {
        this.discountPercentage = generateRandomDiscount();
    }

    private double generateRandomDiscount() {
        int[] possibleDiscounts = {10, 20, 30, 40, 50};
        Random random = new Random();
        int index = random.nextInt(possibleDiscounts.length);
        return possibleDiscounts[index] / 100.0; // Convert to decimal
    }

    @Override
    public double calculatePrice(double basePrice) {
        double discountAmount = basePrice * discountPercentage;
        double discountedPrice = basePrice - discountAmount;

        System.out.println("Applied " + (discountPercentage * 100) + "% discount: -$" + discountAmount);
        return discountedPrice;
    }
}


// Decorator Pattern
interface RoomDecorator extends HotelBookingSystem {
    void addExtras();
}

class BreakfastDecorator implements RoomDecorator {
    private HotelBookingSystem hotelBookingSystem;

    public BreakfastDecorator(HotelBookingSystem hotelBookingSystem) {
        this.hotelBookingSystem = hotelBookingSystem;
    }

    @Override
    public void bookRoom(int roomNumber, double amount) {
        hotelBookingSystem.bookRoom(roomNumber, amount);
        addExtras();
    }

    @Override
    public void addExtras() {
        System.out.println("Added breakfast to the room.");
    }
}

class WifiDecorator implements RoomDecorator {
    private HotelBookingSystem hotelBookingSystem;

    public WifiDecorator(HotelBookingSystem hotelBookingSystem) {
        this.hotelBookingSystem = hotelBookingSystem;
    }

    @Override
    public void bookRoom(int roomNumber, double amount) {
        hotelBookingSystem.bookRoom(roomNumber, amount);
        addExtras();
    }

    @Override
    public void addExtras() {
        System.out.println("Added Wi-Fi to the room.");
    }
}



// Singleton HotelManager
class HotelManager {
    private static HotelManager instance;

    private HotelManager() {
        // Initialization code, if any
    }

    public static HotelManager getInstance() {
        if (instance == null) {
            instance = new HotelManager();
        }
        return instance;
    }

    public void manageHotel() {
        System.out.println("Performing hotel management operations...");
    }

    public void welcomeGuest() {
        System.out.println("Welcome! Enjoy your stay at our hotel.");
    }
}


interface BookingObserver {
    void update(int roomNumber, double amount);
}
class Client implements BookingObserver {
    private String name;

    public Client(String name) {
        this.name = name;
    }

    @Override
    public void update(int roomNumber, double amount) {
        System.out.println("Hello " + name + "! Welcome to our hotel"  + ", which room you would like to book?");
    }
}

interface HotelWorker {
    void work();
}



//Factory pattern
class Doorman implements HotelWorker {
    @Override
    public void work() {
        System.out.println("Doorman is welcoming guests.");
    }
}

class Maid implements HotelWorker {
    @Override
    public void work() {
        System.out.println("Maid is cleaning rooms.");
    }
}

class Chef implements HotelWorker {
    @Override
    public void work() {
        System.out.println("Chef is preparing food.");
    }
}
class WorkerFactory {
    public HotelWorker getWorker(String workerType) {
        if (workerType == null) {
            return null;
        }
        if (workerType.equalsIgnoreCase("DOORMAN")) {
            return new Doorman();
        } else if (workerType.equalsIgnoreCase("MAID")) {
            return new Maid();
        } else if (workerType.equalsIgnoreCase("CHEF")) {
            return new Chef();
        }
        return null;
    }
}




// Usage Example with Singleton, Adapter, Strategy, and Decorator Patterns
class HotelManagementSystem {
    public static void main(String[] args) {
        // Creating observers (clients)
        BookingObserver client1 = new Client("John");
        BookingObserver client2 = new Client("Alice");
        WorkerFactory workerFactory = new WorkerFactory();

        // Get workers from the factory
        HotelWorker doorman = workerFactory.getWorker("DOORMAN");
        HotelWorker maid = workerFactory.getWorker("MAID");
        HotelWorker chef = workerFactory.getWorker("CHEF");

        // Utilize workers
        if (doorman != null) {
            doorman.work();
        }
        if (maid != null) {
            maid.work();
        }
        if (chef != null) {
            chef.work();
        }

        // Notify observers about bookings
        int bookedRoomNumber = 101; // Example room number
        double bookedRoomPrice = 150.0; // Example room price




        ThirdPartyPaymentSystem thirdPartyPaymentSystem = new PaymentSystem();
        HotelBookingSystem paymentAdapter = new PaymentAdapter(thirdPartyPaymentSystem);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Notify Clients about Booking:");
        System.out.println("Press 1 to notify John");
        System.out.println("Press 2 to notify Alice");
        int clientChoice = scanner.nextInt();


        switch (clientChoice) {
            case 1:
                client1.update(bookedRoomNumber, bookedRoomPrice);
                break;
            case 2:
                client2.update(bookedRoomNumber, bookedRoomPrice);
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }


        System.out.print("Enter the amount of cash you have: $");
        double cashAmount = scanner.nextDouble();

        int[] availableRooms = {101, 102, 103};
        double[] roomPrices = {100.0, 120.0, 150.0};

        System.out.println("\nAvailable Rooms:");
        for (int i = 0; i < availableRooms.length; i++) {
            System.out.println(availableRooms[i] + ": $" + roomPrices[i] + " per night");
        }

        System.out.print("\nEnter the room number you want to book: ");
        int selectedRoom = scanner.nextInt();

        if (selectedRoom < 101 || selectedRoom > 103) {
            System.out.println("Invalid room number. Please select from the available rooms.");
            return;
        }

        double selectedRoomPrice = roomPrices[selectedRoom - 101];

        // Select a pricing strategy
        System.out.println("\nSelect a pricing strategy:");
        System.out.println("1. Standard Pricing");
        System.out.println("2. Discounted Pricing");
        int strategyChoice = scanner.nextInt();

        PricingStrategy strategy;
        if (strategyChoice == 1) {
            strategy = new StandardPricingStrategy();
        } else if (strategyChoice == 2) {
            strategy = new DiscountedPricingStrategy();
        } else {
            System.out.println("Invalid strategy choice. Using standard pricing.");
            strategy = new StandardPricingStrategy();
        }

        // Ask if the user wants to add extras
        System.out.print("Do you want to add extras? (B for Breakfast, W for Wi-Fi, N for None): ");
        String addExtras = scanner.next();

        HotelBookingSystem roomBookingSystem = paymentAdapter; // Default, without decoration

        if (addExtras.equalsIgnoreCase("B")) {
            RoomDecorator breakfastDecorator = new BreakfastDecorator(paymentAdapter);
            roomBookingSystem = (HotelBookingSystem) breakfastDecorator; // Decorated booking with breakfast
        } else if (addExtras.equalsIgnoreCase("W")) {
            RoomDecorator wifiDecorator = new WifiDecorator(paymentAdapter);
            roomBookingSystem = (HotelBookingSystem) wifiDecorator; // Decorated booking with Wi-Fi
        }

        // Calculate room price based on the selected strategy and decorator
        double calculatedPrice = strategy.calculatePrice(selectedRoomPrice);

        if (cashAmount >= calculatedPrice) {
            System.out.println("\nBooking room " + selectedRoom + "...");
            roomBookingSystem.bookRoom(selectedRoom, calculatedPrice);


            HotelManager hotelManager = HotelManager.getInstance();
            hotelManager.manageHotel();
            hotelManager.welcomeGuest();
        } else {
            System.out.println("Insufficient cash. Cannot book the room.");
        }



        scanner.close();
    }
}