// EncapsulationDemo.java
// Demonstrating encapsulation principles in Java

public class EncapsulationDemo {
    public static void main(String[] args) {
        System.out.println("=== ENCAPSULATION DEMONSTRATION ===\n");
        
        // Create a Person object using the constructor
        Person person1 = new Person("John Doe", 25, "123-456-7890");
        System.out.println("Person created: " + person1.getName());
        
        // Demonstrate getter methods
        System.out.println("\n--- Getter Methods ---");
        System.out.println("Name: " + person1.getName());
        System.out.println("Age: " + person1.getAge());
        System.out.println("Phone: " + person1.getPhone());
        
        // Demonstrate setter methods with validation
        System.out.println("\n--- Setter Methods with Validation ---");
        
        // Valid age change
        person1.setAge(26);
        System.out.println("Age updated to: " + person1.getAge());
        
        // Invalid age attempt
        System.out.println("Attempting to set invalid age (-5):");
        person1.setAge(-5);
        
        // Valid phone change
        person1.setPhone("987-654-3210");
        System.out.println("Phone updated to: " + person1.getPhone());
        
        // Invalid phone attempt
        System.out.println("Attempting to set invalid phone (empty):");
        person1.setPhone("");
        
        // Demonstrate read-only field
        System.out.println("\n--- Read-only Field Demonstration ---");
        System.out.println("Person ID (read-only): " + person1.getId());
        // person1.id = "new-id"; // This would cause compilation error
        
        // Demonstrate private method usage through public method
        System.out.println("\n--- Private Method Usage ---");
        System.out.println("Formatted info: " + person1.getFormattedInfo());
        
        // Create another person to show unique IDs
        System.out.println("\n--- Multiple Instances ---");
        Person person2 = new Person("Jane Smith", 30, "555-123-4567");
        System.out.println("Second person created: " + person2.getName());
        System.out.println("Person 1 ID: " + person1.getId());
        System.out.println("Person 2 ID: " + person2.getId());
        
        // Demonstrate encapsulation benefits
        System.out.println("\n=== ENCAPSULATION BENEFITS ===");
        System.out.println("1. Data hiding: Internal state is protected");
        System.out.println("2. Validation: Setters ensure data integrity");
        System.out.println("3. Flexibility: Internal implementation can change without affecting users");
        System.out.println("4. Read-only access: Some fields can be made read-only");
    }
}

/**
 * Person class demonstrating encapsulation principles
 * - Private fields
 * - Public getter and setter methods
 * - Data validation
 * - Read-only fields
 * - Private helper methods
 */
class Person {
    // Private fields - cannot be accessed directly from outside the class
    private String name;
    private int age;
    private String phone;
    private final String id; // Read-only field
    
    private static int nextId = 1; // For generating unique IDs
    
    // Constructor - initializes object with validation
    public Person(String name, int age, String phone) {
        this.id = generateId(); // Set read-only field
        setName(name); // Use setter for validation
        setAge(age);   // Use setter for validation
        setPhone(phone); // Use setter for validation
    }
    
    // Public getter methods - provide read access to private fields
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getPhone() {
        return phone;
    }
    
    // Read-only field access - no setter provided
    public String getId() {
        return id;
    }
    
    // Public setter methods with validation - provide write access with controls
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Error: Name cannot be empty or null");
            return;
        }
        this.name = name.trim();
    }
    
    public void setAge(int age) {
        if (age < 0 || age > 150) {
            System.out.println("Error: Age must be between 0 and 150");
            return;
        }
        this.age = age;
    }
    
    public void setPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            System.out.println("Error: Phone cannot be empty or null");
            return;
        }
        this.phone = phone.trim();
    }
    
    // Public method that uses private helper methods
    public String getFormattedInfo() {
        return formatName() + " | Age: " + age + " | Phone: " + formatPhone();
    }
    
    // Private helper method - internal implementation detail
    private String formatName() {
        return "Name: " + name.toUpperCase();
    }
    
    // Another private helper method
    private String formatPhone() {
        return phone.replace("-", ".");
    }
    
    // Private method for generating unique ID
    private String generateId() {
        return "P" + String.format("%04d", nextId++);
    }
    
    // Static method to demonstrate encapsulation at class level
    public static int getTotalPeopleCreated() {
        return nextId - 1;
    }
}

/**
 * BankAccount class demonstrating advanced encapsulation
 * with transaction history and balance protection
 */
class BankAccount {
    private final String accountNumber;
    private double balance;
    private final String owner;
    private final java.util.ArrayList<String> transactionHistory;
    
    public BankAccount(String accountNumber, String owner, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = 0; // Start at 0, then use deposit method
        this.transactionHistory = new java.util.ArrayList<>();
        
        if (initialDeposit > 0) {
            deposit(initialDeposit);
        }
    }
    
    // Read-only access to account number and owner
    public String getAccountNumber() { return accountNumber; }
    public String getOwner() { return owner; }
    
    // Controlled access to balance
    public double getBalance() { return balance; }
    
    // Public methods for operations with validation
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive");
            return;
        }
        balance += amount;
        addTransaction("Deposit: +$" + amount);
        System.out.println("Deposited: $" + amount);
    }
    
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive");
            return;
        }
        if (amount > balance) {
            System.out.println("Insufficient funds");
            return;
        }
        balance -= amount;
        addTransaction("Withdrawal: -$" + amount);
        System.out.println("Withdrew: $" + amount);
    }
    
    // Private method for internal use only
    private void addTransaction(String description) {
        String transaction = java.time.LocalDateTime.now() + " - " + description + 
                           " | Balance: $" + balance;
        transactionHistory.add(transaction);
    }
    
    // Public method to view transaction history (read-only)
    public void printTransactionHistory() {
        System.out.println("\nTransaction History for Account: " + accountNumber);
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}