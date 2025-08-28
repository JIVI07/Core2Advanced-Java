interface Payment {
    void processPayment(double amount);
    String getPaymentDetails();
}

class CreditCardPayment implements Payment {
    private String cardNumber;
    private String cardHolder;
    
    public CreditCardPayment(String cardNumber, String cardHolder) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }
    
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment of $" + amount + 
                          " for card ending with " + cardNumber.substring(cardNumber.length() - 4));
    }
    
    @Override
    public String getPaymentDetails() {
        return "Credit Card: " + cardHolder + " (****" + cardNumber.substring(cardNumber.length() - 4) + ")";
    }
    
    public void validateCard() {
        System.out.println("Validating credit card for " + cardHolder);
    }
}

class PayPalPayment implements Payment {
    private String email;
    private String password;
    
    public PayPalPayment(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment of $" + amount + " for " + email);
    }
    
    @Override
    public String getPaymentDetails() {
        return "PayPal: " + email;
    }
    
    public void sendConfirmationEmail() {
        System.out.println("Sending confirmation email to " + email);
    }
}

class BankTransferPayment implements Payment {
    private String accountNumber;
    private String bankName;
    
    public BankTransferPayment(String accountNumber, String bankName) {
        this.accountNumber = accountNumber;
        this.bankName = bankName;
    }
    
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing bank transfer of $" + amount + 
                          " to account " + accountNumber + " at " + bankName);
    }
    
    @Override
    public String getPaymentDetails() {
        return "Bank Transfer: " + bankName + " (Acct: " + accountNumber + ")";
    }
    
    public void generateReceipt() {
        System.out.println("Generating bank transfer receipt for account " + accountNumber);
    }
}

abstract class Shape {
    protected String color;
    
    public Shape(String color) {
        this.color = color;
    }
    
    public abstract double calculateArea();
    public abstract double calculatePerimeter();
    
    public void displayColor() {
        System.out.println("Shape color: " + color);
    }
    
    public void draw() {
        System.out.println("Drawing a " + color + " shape");
    }
}

class Circle extends Shape {
    private double radius;
    
    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a " + color + " circle with radius " + radius);
    }
    
    public double getDiameter() {
        return 2 * radius;
    }
}

class Rectangle extends Shape {
    private double width;
    private double height;
    
    public Rectangle(String color, double width, double height) {
        super(color);
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double calculateArea() {
        return width * height;
    }
    
    @Override
    public double calculatePerimeter() {
        return 2 * (width + height);
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a " + color + " rectangle " + width + "x" + height);
    }
    
    public boolean isSquare() {
        return width == height;
    }
}

class Triangle extends Shape {
    private double sideA;
    private double sideB;
    private double sideC;
    
    public Triangle(String color, double sideA, double sideB, double sideC) {
        super(color);
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }
    
    @Override
    public double calculateArea() {
        double s = (sideA + sideB + sideC) / 2;
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
    }
    
    @Override
    public double calculatePerimeter() {
        return sideA + sideB + sideC;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a " + color + " triangle with sides " + 
                          sideA + ", " + sideB + ", " + sideC);
    }
    
    public boolean isEquilateral() {
        return sideA == sideB && sideB == sideC;
    }
}

class Employee {
    protected String name;
    protected double baseSalary;
    
    public Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }
    
    public double calculateSalary() {
        return baseSalary;
    }
    
    public void work() {
        System.out.println(name + " is working");
    }
    
    public String getDetails() {
        return "Employee: " + name + ", Base Salary: $" + baseSalary;
    }
}

class Manager extends Employee {
    private double bonus;
    
    public Manager(String name, double baseSalary, double bonus) {
        super(name, baseSalary);
        this.bonus = bonus;
    }
    
    @Override
    public double calculateSalary() {
        return baseSalary + bonus;
    }
    
    @Override
    public void work() {
        System.out.println(name + " is managing the team");
    }
    
    @Override
    public String getDetails() {
        return "Manager: " + name + ", Total Salary: $" + calculateSalary() + " (Base: $" + baseSalary + ", Bonus: $" + bonus + ")";
    }
    
    public void conductMeeting() {
        System.out.println(name + " is conducting a team meeting");
    }
}

class Developer extends Employee {
    private String programmingLanguage;
    private int overtimeHours;
    private double overtimeRate;
    
    public Developer(String name, double baseSalary, String programmingLanguage, int overtimeHours, double overtimeRate) {
        super(name, baseSalary);
        this.programmingLanguage = programmingLanguage;
        this.overtimeHours = overtimeHours;
        this.overtimeRate = overtimeRate;
    }
    
    @Override
    public double calculateSalary() {
        return baseSalary + (overtimeHours * overtimeRate);
    }
    
    @Override
    public void work() {
        System.out.println(name + " is coding in " + programmingLanguage);
    }
    
    @Override
    public String getDetails() {
        return "Developer: " + name + ", Language: " + programmingLanguage + 
               ", Total Salary: $" + calculateSalary() + " (Overtime: " + overtimeHours + " hours)";
    }
    
    public void debugCode() {
        System.out.println(name + " is debugging " + programmingLanguage + " code");
    }
}

class Animal {
    public void makeSound() {
        System.out.println("Animal makes a sound");
    }
    
    public void eat() {
        System.out.println("Animal is eating");
    }
}

class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Dog barks: Woof! Woof!");
    }
    
    @Override
    public void eat() {
        System.out.println("Dog is eating dog food");
    }
    
    public void fetch() {
        System.out.println("Dog is fetching the ball");
    }
}

class Cat extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Cat meows: Meow! Meow!");
    }
    
    @Override
    public void eat() {
        System.out.println("Cat is eating cat food");
    }
    
    public void climb() {
        System.out.println("Cat is climbing the tree");
    }
}

public class PolymorphismDemo {
    
    public void demonstrateRuntimePolymorphism() {
        System.out.println("=== RUNTIME POLYMORPHISM (METHOD OVERRIDING) ===");
        
        Animal myDog = new Dog();
        Animal myCat = new Cat();
        
        myDog.makeSound();
        myDog.eat();
        
        myCat.makeSound();
        myCat.eat();
        
        if (myDog instanceof Dog) {
            Dog realDog = (Dog) myDog;
            realDog.fetch();
        }
        
        if (myCat instanceof Cat) {
            Cat realCat = (Cat) myCat;
            realCat.climb();
        }
        System.out.println();
    }
    
    public void demonstrateCompileTimePolymorphism() {
        System.out.println("=== COMPILE-TIME POLYMORPHISM (METHOD OVERLOADING) ===");
        
        MathOperations math = new MathOperations();
        
        System.out.println("Add integers: " + math.add(5, 3));
        System.out.println("Add doubles: " + math.add(5.5, 3.3));
        System.out.println("Add three integers: " + math.add(5, 3, 2));
        System.out.println("Add strings: " + math.add("Hello", " World"));
        System.out.println();
    }
    
    public void demonstrateInterfacePolymorphism() {
        System.out.println("=== INTERFACE POLYMORPHISM ===");
        
        Payment[] payments = {
            new CreditCardPayment("1234567812345678", "John Doe"),
            new PayPalPayment("john.doe@email.com", "password123"),
            new BankTransferPayment("9876543210", "Bank of America")
        };
        
        for (Payment payment : payments) {
            System.out.println(payment.getPaymentDetails());
            payment.processPayment(100.0);
            
            if (payment instanceof CreditCardPayment) {
                CreditCardPayment cc = (CreditCardPayment) payment;
                cc.validateCard();
            } else if (payment instanceof PayPalPayment) {
                PayPalPayment pp = (PayPalPayment) payment;
                pp.sendConfirmationEmail();
            } else if (payment instanceof BankTransferPayment) {
                BankTransferPayment bt = (BankTransferPayment) payment;
                bt.generateReceipt();
            }
            System.out.println();
        }
    }
    
    public void demonstrateAbstractClassPolymorphism() {
        System.out.println("=== ABSTRACT CLASS POLYMORPHISM ===");
        
        Shape[] shapes = {
            new Circle("Red", 5.0),
            new Rectangle("Blue", 4.0, 6.0),
            new Triangle("Green", 3.0, 4.0, 5.0)
        };
        
        for (Shape shape : shapes) {
            shape.draw();
            shape.displayColor();
            System.out.println("Area: " + shape.calculateArea());
            System.out.println("Perimeter: " + shape.calculatePerimeter());
            
            if (shape instanceof Circle) {
                Circle circle = (Circle) shape;
                System.out.println("Diameter: " + circle.getDiameter());
            } else if (shape instanceof Rectangle) {
                Rectangle rectangle = (Rectangle) shape;
                System.out.println("Is square: " + rectangle.isSquare());
            } else if (shape instanceof Triangle) {
                Triangle triangle = (Triangle) shape;
                System.out.println("Is equilateral: " + triangle.isEquilateral());
            }
            System.out.println();
        }
    }
    
    public void demonstrateEmployeePolymorphism() {
        System.out.println("=== EMPLOYEE POLYMORPHISM ===");
        
        Employee[] employees = {
            new Employee("Alice", 50000),
            new Manager("Bob", 70000, 15000),
            new Developer("Charlie", 60000, "Java", 10, 50.0)
        };
        
        for (Employee employee : employees) {
            employee.work();
            System.out.println(employee.getDetails());
            System.out.println("Calculated Salary: $" + employee.calculateSalary());
            
            if (employee instanceof Manager) {
                Manager manager = (Manager) employee;
                manager.conductMeeting();
            } else if (employee instanceof Developer) {
                Developer developer = (Developer) employee;
                developer.debugCode();
            }
            System.out.println();
        }
    }
    
    public void demonstratePolymorphicParameters() {
        System.out.println("=== POLYMORPHIC PARAMETERS ===");
        
        processShape(new Circle("Yellow", 7.0));
        processShape(new Rectangle("Purple", 5.0, 8.0));
        processShape(new Triangle("Orange", 6.0, 8.0, 10.0));
        System.out.println();
    }
    
    public void processShape(Shape shape) {
        System.out.println("Processing shape: " + shape.getClass().getSimpleName());
        shape.draw();
        System.out.println("Area: " + shape.calculateArea());
    }
    
    public void demonstratePolymorphicReturnTypes() {
        System.out.println("=== POLYMORPHIC RETURN TYPES ===");
        
        Payment payment1 = createPayment("credit", "John Doe");
        Payment payment2 = createPayment("paypal", "Jane Smith");
        Payment payment3 = createPayment("bank", "Bob Johnson");
        
        payment1.processPayment(200.0);
        payment2.processPayment(300.0);
        payment3.processPayment(400.0);
        System.out.println();
    }
    
    public Payment createPayment(String type, String name) {
        switch (type.toLowerCase()) {
            case "credit":
                return new CreditCardPayment("1111222233334444", name);
            case "paypal":
                return new PayPalPayment(name.toLowerCase() + "@email.com", "pass123");
            case "bank":
                return new BankTransferPayment("1234567890", "Chase Bank");
            default:
                return new CreditCardPayment("0000000000000000", "Default User");
        }
    }
    
    public static void main(String[] args) {
        PolymorphismDemo demo = new PolymorphismDemo();
        
        System.out.println("JAVA POLYMORPHISM DEMONSTRATION");
        System.out.println("===============================");
        
        demo.demonstrateRuntimePolymorphism();
        demo.demonstrateCompileTimePolymorphism();
        demo.demonstrateInterfacePolymorphism();
        demo.demonstrateAbstractClassPolymorphism();
        demo.demonstrateEmployeePolymorphism();
        demo.demonstratePolymorphicParameters();
        demo.demonstratePolymorphicReturnTypes();
        
        System.out.println("=== DEMONSTRATION COMPLETE ===");
    }
}

class MathOperations {
    public int add(int a, int b) {
        return a + b;
    }
    
    public double add(double a, double b) {
        return a + b;
    }
    
    public int add(int a, int b, int c) {
        return a + b + c;
    }
    
    public String add(String a, String b) {
        return a + b;
    }
}