import java.util.*;

public class Application {

    public static void main(String[] args) {
        System.out.println("=== Design Patterns Demonstration ===\n");

        demonstrateSingletonPattern();
        demonstrateFactoryPattern();
        demonstrateBuilderPattern();
        demonstrateObserverPattern();
        demonstrateStrategyPattern();
    }

    private static void demonstrateSingletonPattern() {
        System.out.println("=== Singleton Pattern Demo ===");
        
        SingletonPattern.EagerSingleton eager1 = SingletonPattern.EagerSingleton.getInstance();
        SingletonPattern.EagerSingleton eager2 = SingletonPattern.EagerSingleton.getInstance();
        eager1.showMessage();
        System.out.println("Eager instances equal: " + (eager1 == eager2));
        
        SingletonPattern.EnumSingleton enum1 = SingletonPattern.EnumSingleton.INSTANCE;
        SingletonPattern.EnumSingleton enum2 = SingletonPattern.EnumSingleton.INSTANCE;
        enum1.showMessage();
        System.out.println("Enum instances equal: " + (enum1 == enum2));
        System.out.println();
    }

    private static void demonstrateFactoryPattern() {
        System.out.println("=== Factory Pattern Demo ===");
        
        FactoryPattern.ShapeFactory circleFactory = new FactoryPattern.CircleFactory();
        FactoryPattern.Shape circle = circleFactory.createShapeWithParams(5.0);
        circle.draw();
        System.out.println("Circle Area: " + circle.calculateArea());
        
        FactoryPattern.ShapeFactory rectFactory = FactoryPattern.ShapeFactoryRegistry.getFactory(
            FactoryPattern.ShapeFactoryRegistry.ShapeType.RECTANGLE);
        FactoryPattern.Shape rectangle = rectFactory.createShapeWithParams(4.0, 6.0);
        rectangle.draw();
        System.out.println("Rectangle Area: " + rectangle.calculateArea());
        System.out.println();
    }

    private static void demonstrateBuilderPattern() {
        System.out.println("=== Builder Pattern Demo ===");
        
        BuilderPattern.Computer computer = BuilderPattern.Computer.builder()
            .cpu("Intel i7-12700K")
            .ram("32GB DDR4")
            .storage("1TB NVMe SSD")
            .gpu("NVIDIA RTX 3080")
            .addPeripheral("Gaming Mouse")
            .bluetooth(true)
            .wifi(true)
            .build();
        System.out.println(computer);
        
        BuilderPattern.Person person = BuilderPattern.Person.builder()
            .firstName("John")
            .lastName("Doe")
            .age(30)
            .email("john.doe@example.com")
            .build();
        System.out.println(person);
        System.out.println();
    }

    private static void demonstrateObserverPattern() {
        System.out.println("=== Observer Pattern Demo ===");
        
        ObserverPattern.NewsAgency agency = new ObserverPattern.NewsAgency();
        agency.addObserver(new ObserverPattern.Newspaper("New York Times"));
        agency.addObserver(new ObserverPattern.TVChannel("CNN"));
        
        agency.setNews("Breaking: Java 21 released with new features!");
        agency.setNews("Update: AI revolution continues");
        
        ObserverPattern.StockMarket market = new ObserverPattern.StockMarket();
        market.registerObserver(new ObserverPattern.StockTrader("John", "AAPL", "GOOGL"));
        
        market.setStockPrice("AAPL", 145.50);
        market.setStockPrice("GOOGL", 2750.80);
        System.out.println();
    }

    private static void demonstrateStrategyPattern() {
        System.out.println("=== Strategy Pattern Demo ===");
        
        StrategyPattern.ShoppingCart cart = new StrategyPattern.ShoppingCart();
        cart.addItem(new StrategyPattern.Item("Laptop", 999.99));
        cart.addItem(new StrategyPattern.Item("Mouse", 25.50));
        
        cart.setPaymentStrategy(new StrategyPattern.CreditCardPayment(
            "1234-5678-9012-3456", "John Doe", "123", "12/25"));
        cart.checkout();
        
        StrategyPattern.CompressionContext compressor = new StrategyPattern.CompressionContext();
        compressor.setCompressionStrategy(new StrategyPattern.ZipCompression());
        compressor.compressFile("document.txt");
        
        StrategyPattern.Product product = new StrategyPattern.Product("Smartphone", 699.99);
        product.setDiscountStrategy(new StrategyPattern.PercentageDiscount(15));
        System.out.println("Discounted price: $" + product.getFinalPrice());
        System.out.println();
    }

    public static class SingletonPattern {
        public static class EagerSingleton {
            private static final EagerSingleton INSTANCE = new EagerSingleton();
            private EagerSingleton() {}
            public static EagerSingleton getInstance() { return INSTANCE; }
            public void showMessage() { System.out.println("Hello from EagerSingleton!"); }
        }
        
        public enum EnumSingleton {
            INSTANCE;
            public void showMessage() { System.out.println("Hello from EnumSingleton!"); }
        }
    }

    public static class FactoryPattern {
        public interface Shape {
            void draw();
            double calculateArea();
        }
        
        public static class Circle implements Shape {
            private double radius;
            public Circle(double radius) { this.radius = radius; }
            @Override public void draw() { System.out.println("Drawing Circle with radius: " + radius); }
            @Override public double calculateArea() { return Math.PI * radius * radius; }
        }
        
        public static class Rectangle implements Shape {
            private double width, height;
            public Rectangle(double width, double height) { this.width = width; this.height = height; }
            @Override public void draw() { System.out.println("Drawing Rectangle: " + width + "x" + height); }
            @Override public double calculateArea() { return width * height; }
        }
        
        public interface ShapeFactory {
            Shape createShapeWithParams(double... params);
        }
        
        public static class CircleFactory implements ShapeFactory {
            @Override public Shape createShapeWithParams(double... params) {
                if (params.length < 1) throw new IllegalArgumentException("Circle requires radius");
                return new Circle(params[0]);
            }
        }
        
        public static class RectangleFactory implements ShapeFactory {
            @Override public Shape createShapeWithParams(double... params) {
                if (params.length < 2) throw new IllegalArgumentException("Rectangle requires width and height");
                return new Rectangle(params[0], params[1]);
            }
        }
        
        public static class ShapeFactoryRegistry {
            public enum ShapeType { CIRCLE, RECTANGLE, SQUARE, TRIANGLE }
            public static ShapeFactory getFactory(ShapeType type) {
                switch (type) {
                    case CIRCLE: return new CircleFactory();
                    case RECTANGLE: return new RectangleFactory();
                    default: throw new IllegalArgumentException("Unknown shape type");
                }
            }
        }
    }

    public static class BuilderPattern {
        public static class Computer {
            private final String cpu;
            private final String ram;
            private final String storage;
            private final String gpu;
            private final List<String> peripherals;
            private final boolean hasBluetooth;
            private final boolean hasWifi;

            private Computer(Builder builder) {
                this.cpu = builder.cpu;
                this.ram = builder.ram;
                this.storage = builder.storage;
                this.gpu = builder.gpu;
                this.peripherals = builder.peripherals;
                this.hasBluetooth = builder.hasBluetooth;
                this.hasWifi = builder.hasWifi;
            }

            public static Builder builder() { return new Builder(); }

            public static class Builder {
                private String cpu;
                private String ram;
                private String storage;
                private String gpu;
                private List<String> peripherals = new ArrayList<>();
                private boolean hasBluetooth;
                private boolean hasWifi;

                public Builder cpu(String cpu) { this.cpu = cpu; return this; }
                public Builder ram(String ram) { this.ram = ram; return this; }
                public Builder storage(String storage) { this.storage = storage; return this; }
                public Builder gpu(String gpu) { this.gpu = gpu; return this; }
                public Builder addPeripheral(String peripheral) { this.peripherals.add(peripheral); return this; }
                public Builder bluetooth(boolean hasBluetooth) { this.hasBluetooth = hasBluetooth; return this; }
                public Builder wifi(boolean hasWifi) { this.hasWifi = hasWifi; return this; }

                public Computer build() {
                    validate();
                    return new Computer(this);
                }

                private void validate() {
                    if (cpu == null) throw new IllegalStateException("CPU is required");
                    if (ram == null) throw new IllegalStateException("RAM is required");
                    if (storage == null) throw new IllegalStateException("Storage is required");
                }
            }

            @Override
            public String toString() {
                return String.format("Computer[CPU=%s, RAM=%s, Storage=%s, GPU=%s, Peripherals=%s, Bluetooth=%s, WiFi=%s]",
                    cpu, ram, storage, gpu, peripherals, hasBluetooth, hasWifi);
            }
        }

        public static class Person {
            private final String firstName;
            private final String lastName;
            private final int age;
            private final String email;

            private Person(PersonBuilder builder) {
                this.firstName = builder.firstName;
                this.lastName = builder.lastName;
                this.age = builder.age;
                this.email = builder.email;
            }

            public static PersonBuilder builder() { return new PersonBuilder(); }

            public static class PersonBuilder {
                private String firstName;
                private String lastName;
                private int age;
                private String email;

                public PersonBuilder firstName(String firstName) { this.firstName = firstName; return this; }
                public PersonBuilder lastName(String lastName) { this.lastName = lastName; return this; }
                public PersonBuilder age(int age) { this.age = age; return this; }
                public PersonBuilder email(String email) { this.email = email; return this; }

                public Person build() {
                    validate();
                    return new Person(this);
                }

                private void validate() {
                    if (firstName == null || firstName.trim().isEmpty()) throw new IllegalStateException("First name is required");
                    if (lastName == null || lastName.trim().isEmpty()) throw new IllegalStateException("Last name is required");
                    if (age < 0) throw new IllegalStateException("Age must be non-negative");
                }
            }

            @Override
            public String toString() {
                return String.format("Person[Name=%s %s, Age=%d, Email=%s]", firstName, lastName, age, email);
            }
        }
    }

    public static class ObserverPattern {
        public static class NewsAgency {
            private String news;
            private final List<NewsObserver> observers = new ArrayList<>();

            public void addObserver(NewsObserver observer) { observers.add(observer); }
            public void removeObserver(NewsObserver observer) { observers.remove(observer); }

            public void setNews(String news) {
                this.news = news;
                notifyObservers();
            }

            private void notifyObservers() {
                for (NewsObserver observer : observers) {
                    observer.update(news);
                }
            }
        }

        public interface NewsObserver {
            void update(String news);
        }

        public static class Newspaper implements NewsObserver {
            private final String name;
            public Newspaper(String name) { this.name = name; }
            @Override public void update(String news) { System.out.println(name + " newspaper received news: " + news); }
        }

        public static class TVChannel implements NewsObserver {
            private final String name;
            public TVChannel(String name) { this.name = name; }
            @Override public void update(String news) { System.out.println(name + " TV channel broadcasting: " + news); }
        }

        public static class StockMarket {
            private final List<Observer<StockPrice>> observers = new ArrayList<>();
            private final Map<String, Double> stockPrices = new HashMap<>();

            public void setStockPrice(String symbol, double price) {
                stockPrices.put(symbol, price);
                notifyObservers(new StockPrice(symbol, price, new Date()));
            }

            public void registerObserver(Observer<StockPrice> observer) { observers.add(observer); }

            private void notifyObservers(StockPrice data) {
                for (Observer<StockPrice> observer : observers) {
                    observer.update(data);
                }
            }
        }

        public static class StockPrice {
            private final String symbol;
            private final double price;
            private final Date timestamp;
            public StockPrice(String symbol, double price, Date timestamp) {
                this.symbol = symbol; this.price = price; this.timestamp = timestamp;
            }
            public String getSymbol() { return symbol; }
            public double getPrice() { return price; }
        }

        public interface Observer<T> {
            void update(T data);
        }

        public static class StockTrader implements Observer<StockPrice> {
            private final String name;
            private final Set<String> watchedStocks;
            public StockTrader(String name, String... stocks) {
                this.name = name;
                this.watchedStocks = new HashSet<>(Arrays.asList(stocks));
            }
            @Override public void update(StockPrice data) {
                if (watchedStocks.contains(data.getSymbol())) {
                    System.out.printf("%s: %s is now $%.2f%n", name, data.getSymbol(), data.getPrice());
                }
            }
        }
    }

    public static class StrategyPattern {
        public interface PaymentStrategy {
            void pay(double amount);
        }

        public static class CreditCardPayment implements PaymentStrategy {
            private String cardNumber;
            private String name;
            private String cvv;
            private String expiryDate;
            public CreditCardPayment(String cardNumber, String name, String cvv, String expiryDate) {
                this.cardNumber = cardNumber; this.name = name; this.cvv = cvv; this.expiryDate = expiryDate;
            }
            @Override public void pay(double amount) {
                System.out.println("Paid " + amount + " using Credit Card: " + cardNumber);
            }
        }

        public static class ShoppingCart {
            private List<Item> items = new ArrayList<>();
            private PaymentStrategy paymentStrategy;

            public void addItem(Item item) { items.add(item); }
            public void setPaymentStrategy(PaymentStrategy paymentStrategy) { this.paymentStrategy = paymentStrategy; }

            public void checkout() {
                double total = items.stream().mapToDouble(Item::getPrice).sum();
                if (paymentStrategy != null) {
                    paymentStrategy.pay(total);
                    items.clear();
                }
            }
        }

        public static class Item {
            private String name;
            private double price;
            public Item(String name, double price) { this.name = name; this.price = price; }
            public double getPrice() { return price; }
        }

        public interface CompressionStrategy {
            void compress(String file);
        }

        public static class ZipCompression implements CompressionStrategy {
            @Override public void compress(String file) { System.out.println("Compressing " + file + " using ZIP format"); }
        }

        public static class CompressionContext {
            private CompressionStrategy strategy;
            public void setCompressionStrategy(CompressionStrategy strategy) { this.strategy = strategy; }
            public void compressFile(String file) {
                if (strategy != null) strategy.compress(file);
            }
        }

        public interface DiscountStrategy {
            double applyDiscount(double originalPrice);
        }

        public static class PercentageDiscount implements DiscountStrategy {
            private double percentage;
            public PercentageDiscount(double percentage) { this.percentage = percentage; }
            @Override public double applyDiscount(double originalPrice) { return originalPrice * (1 - percentage / 100); }
        }

        public static class Product {
            private String name;
            private double price;
            private DiscountStrategy discountStrategy;
            public Product(String name, double price) {
                this.name = name; this.price = price; this.discountStrategy = originalPrice -> originalPrice;
            }
            public void setDiscountStrategy(DiscountStrategy discountStrategy) { this.discountStrategy = discountStrategy; }
            public double getFinalPrice() { return discountStrategy.applyDiscount(price); }
        }
    }
}