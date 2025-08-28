class Person {
    private String name;
    private int age;
    private String email;
    
    public static int personCount = 0;
    
    public Person() {
        this("Unknown", 0, "unknown@email.com");
    }
    
    public Person(String name, int age) {
        this(name, age, name.toLowerCase() + "@email.com");
    }
    
    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
        personCount++;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        if (age >= 0) {
            this.age = age;
        }
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void introduce() {
        System.out.println("Hello, my name is " + name + ", I'm " + age + " years old.");
    }
    
    public boolean isAdult() {
        return age >= 18;
    }
    
    public static void displayPersonCount() {
        System.out.println("Total persons created: " + personCount);
    }
    
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + ", email='" + email + "'}";
    }
}

class Student extends Person {
    private String studentId;
    private double gpa;
    
    public Student(String name, int age, String studentId, double gpa) {
        super(name, age);
        this.studentId = studentId;
        this.gpa = gpa;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public double getGpa() {
        return gpa;
    }
    
    public void setGpa(double gpa) {
        if (gpa >= 0.0 && gpa <= 4.0) {
            this.gpa = gpa;
        }
    }
    
    @Override
    public void introduce() {
        super.introduce();
        System.out.println("I am a student with ID: " + studentId + " and GPA: " + gpa);
    }
    
    public String getGradeLevel() {
        if (gpa >= 3.5) return "Excellent";
        else if (gpa >= 3.0) return "Good";
        else if (gpa >= 2.0) return "Average";
        else return "Needs improvement";
    }
    
    @Override
    public String toString() {
        return "Student{" +
               "name='" + getName() + '\'' +
               ", age=" + getAge() +
               ", studentId='" + studentId + '\'' +
               ", gpa=" + gpa +
               '}';
    }
}

interface Drawable {
    void draw();
    double calculateArea();
}

class Circle implements Drawable {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a circle with radius: " + radius);
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
    
    public double getCircumference() {
        return 2 * Math.PI * radius;
    }
}

abstract class Animal {
    protected String name;
    protected String species;
    
    public Animal(String name, String species) {
        this.name = name;
        this.species = species;
    }
    
    public abstract void makeSound();
    
    public void sleep() {
        System.out.println(name + " is sleeping...");
    }
    
    public String getName() {
        return name;
    }
}

class Dog extends Animal {
    public Dog(String name) {
        super(name, "Canine");
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " says: Woof! Woof!");
    }
    
    public void fetch() {
        System.out.println(name + " is fetching the ball!");
    }
}

public class ClassAndObjectDemo {
    
    public void demonstrateClassAndObject() {
        System.out.println("=== CLASS AND OBJECT DEMONSTRATION ===\n");
        
        Person person1 = new Person();
        Person person2 = new Person("Alice", 25);
        Person person3 = new Person("Bob", 30, "bob@company.com");
        
        System.out.println("Person 1: " + person1);
        System.out.println("Person 2: " + person2);
        System.out.println("Person 3: " + person3);
        
        person1.setName("Charlie");
        person1.setAge(28);
        person1.setEmail("charlie@email.com");
        
        System.out.println("\nAfter modification:");
        System.out.println("Person 1: " + person1);
        
        System.out.println("\n=== METHOD CALLS ===");
        person1.introduce();
        person2.introduce();
        person3.introduce();
        
        System.out.println("\nIs Alice adult? " + person2.isAdult());
        System.out.println("Is Unknown adult? " + person1.isAdult());
        
        Person.displayPersonCount();
    }
    
    public void demonstrateInheritance() {
        System.out.println("\n=== INHERITANCE DEMONSTRATION ===");
        
        Student student1 = new Student("David", 20, "S12345", 3.8);
        Student student2 = new Student("Eva", 22, "S67890", 2.5);
        
        System.out.println("Student 1: " + student1);
        System.out.println("Student 2: " + student2);
        
        System.out.println("\n=== POLYMORPHISM ===");
        student1.introduce();
        student2.introduce();
        
        System.out.println("\nDavid's grade level: " + student1.getGradeLevel());
        System.out.println("Eva's grade level: " + student2.getGradeLevel());
        
        Person personRef = student1;
        System.out.println("\nPolymorphic method call:");
        personRef.introduce();
    }
    
    public void demonstrateAbstraction() {
        System.out.println("\n=== ABSTRACTION AND INTERFACES ===");
        
        Circle circle = new Circle(5.0);
        circle.draw();
        System.out.println("Circle area: " + circle.calculateArea());
        System.out.println("Circle circumference: " + circle.getCircumference());
        
        Drawable drawable = circle;
        System.out.println("Area via interface: " + drawable.calculateArea());
    }
    
    public void demonstratePolymorphism() {
        System.out.println("\n=== POLYMORPHISM WITH ABSTRACT CLASSES ===");
        
        Animal dog = new Dog("Buddy");
        dog.makeSound();
        dog.sleep();
        
        if (dog instanceof Dog) {
            Dog realDog = (Dog) dog;
            realDog.fetch();
        }
    }
    
    public void demonstrateEncapsulation() {
        System.out.println("\n=== ENCAPSULATION ===");
        
        Person person = new Person("Frank", 35);
        System.out.println("Original age: " + person.getAge());
        
        person.setAge(-5);
        System.out.println("After setting invalid age: " + person.getAge());
        
        person.setAge(40);
        System.out.println("After setting valid age: " + person.getAge());
    }
    
    public void demonstrateObjectMethods() {
        System.out.println("\n=== OBJECT CLASS METHODS ===");
        
        Person p1 = new Person("Alice", 25);
        Person p2 = new Person("Alice", 25);
        Person p3 = p1;
        
        System.out.println("p1.equals(p2): " + p1.equals(p2));
        System.out.println("p1.equals(p3): " + p1.equals(p3));
        System.out.println("p1 hashCode: " + p1.hashCode());
        System.out.println("p2 hashCode: " + p2.hashCode());
        System.out.println("p3 hashCode: " + p3.hashCode());
        System.out.println("p1 toString: " + p1.toString());
        System.out.println("p1 class: " + p1.getClass().getSimpleName());
    }
    
    public void demonstrateComposition() {
        System.out.println("\n=== COMPOSITION ===");
        
        class Address {
            private String street;
            private String city;
            private String zipCode;
            
            public Address(String street, String city, String zipCode) {
                this.street = street;
                this.city = city;
                this.zipCode = zipCode;
            }
            
            @Override
            public String toString() {
                return street + ", " + city + ", " + zipCode;
            }
        }
        
        class Employee {
            private String name;
            private Address address;
            
            public Employee(String name, Address address) {
                this.name = name;
                this.address = address;
            }
            
            @Override
            public String toString() {
                return "Employee{name='" + name + "', address=" + address + "}";
            }
        }
        
        Address addr = new Address("123 Main St", "New York", "10001");
        Employee emp = new Employee("John Doe", addr);
        
        System.out.println(emp);
    }
    
    public static void main(String[] args) {
        ClassAndObjectDemo demo = new ClassAndObjectDemo();
        
        demo.demonstrateClassAndObject();
        demo.demonstrateInheritance();
        demo.demonstrateAbstraction();
        demo.demonstratePolymorphism();
        demo.demonstrateEncapsulation();
        demo.demonstrateObjectMethods();
        demo.demonstrateComposition();
        
        System.out.println("\n=== DEMONSTRATION COMPLETE ===");
    }
}