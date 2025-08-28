import java.util.*;
import java.util.stream.Collectors;

public class ListDemo {
    
    public static void main(String[] args) {
        System.out.println("=== JAVA LIST DEMONSTRATION ===\n");
        
        // 1. ArrayList Demo
        arrayListDemo();
        
        // 2. LinkedList Demo
        linkedListDemo();
        
        // 3. Vector Demo
        vectorDemo();
        
        // 4. Stack Demo
        stackDemo();
        
        // 5. Common List Operations
        commonOperationsDemo();
        
        // 6. List with Custom Objects
        customObjectListDemo();
        
        // 7. Java 8+ Features with Lists
        java8FeaturesDemo();
    }
    
    // 1. ArrayList Demonstration
    private static void arrayListDemo() {
        System.out.println("1. ARRAYLIST DEMO");
        System.out.println("=================");
        
        // Creating ArrayList
        List<String> arrayList = new ArrayList<>();
        
        // Adding elements
        arrayList.add("Apple");
        arrayList.add("Banana");
        arrayList.add("Cherry");
        arrayList.add("Date");
        arrayList.add("Elderberry");
        
        System.out.println("Initial ArrayList: " + arrayList);
        
        // Accessing elements
        System.out.println("Element at index 2: " + arrayList.get(2));
        System.out.println("First element: " + arrayList.get(0));
        System.out.println("Last element: " + arrayList.get(arrayList.size() - 1));
        
        // Modifying elements
        arrayList.set(1, "Blueberry");
        System.out.println("After modifying index 1: " + arrayList);
        
        // Removing elements
        arrayList.remove("Date");
        arrayList.remove(0);
        System.out.println("After removals: " + arrayList);
        
        // Checking if list contains element
        System.out.println("Contains 'Cherry': " + arrayList.contains("Cherry"));
        System.out.println("Contains 'Mango': " + arrayList.contains("Mango"));
        
        // Size and emptiness check
        System.out.println("Size: " + arrayList.size());
        System.out.println("Is empty: " + arrayList.isEmpty());
        
        System.out.println();
    }
    
    // 2. LinkedList Demonstration
    private static void linkedListDemo() {
        System.out.println("2. LINKEDLIST DEMO");
        System.out.println("==================");
        
        LinkedList<Integer> linkedList = new LinkedList<>();
        
        // Adding elements
        linkedList.add(10);
        linkedList.add(20);
        linkedList.add(30);
        linkedList.addFirst(5);   // Add at beginning
        linkedList.addLast(40);   // Add at end
        
        System.out.println("LinkedList: " + linkedList);
        
        // LinkedList specific methods
        System.out.println("First element: " + linkedList.getFirst());
        System.out.println("Last element: " + linkedList.getLast());
        System.out.println("Peek (retrieve first): " + linkedList.peek());
        System.out.println("Poll (remove and return first): " + linkedList.poll());
        System.out.println("After poll: " + linkedList);
        
        // Stack operations
        linkedList.push(15); // Push to front (like stack)
        System.out.println("After push(15): " + linkedList);
        System.out.println("Pop: " + linkedList.pop());
        System.out.println("After pop: " + linkedList);
        
        System.out.println();
    }
    
    // 3. Vector Demonstration
    private static void vectorDemo() {
        System.out.println("3. VECTOR DEMO");
        System.out.println("==============");
        
        Vector<String> vector = new Vector<>();
        
        // Adding elements
        vector.add("Java");
        vector.add("Python");
        vector.add("C++");
        vector.add("JavaScript");
        
        System.out.println("Vector: " + vector);
        System.out.println("Capacity: " + vector.capacity());
        System.out.println("Size: " + vector.size());
        
        // Enumeration (legacy way)
        System.out.print("Enumeration elements: ");
        Enumeration<String> enumeration = vector.elements();
        while (enumeration.hasMoreElements()) {
            System.out.print(enumeration.nextElement() + " ");
        }
        System.out.println();
        
        System.out.println();
    }
    
    // 4. Stack Demonstration
    private static void stackDemo() {
        System.out.println("4. STACK DEMO");
        System.out.println("=============");
        
        Stack<String> stack = new Stack<>();
        
        // Push operations
        stack.push("First");
        stack.push("Second");
        stack.push("Third");
        stack.push("Fourth");
        
        System.out.println("Stack: " + stack);
        System.out.println("Top element: " + stack.peek());
        System.out.println("Search 'Second': " + stack.search("Second"));
        
        // Pop operations
        System.out.println("Popped: " + stack.pop());
        System.out.println("Popped: " + stack.pop());
        System.out.println("Stack after pops: " + stack);
        
        System.out.println();
    }
    
    // 5. Common List Operations
    private static void commonOperationsDemo() {
        System.out.println("5. COMMON LIST OPERATIONS");
        System.out.println("=========================");
        
        List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 9, 3, 7, 4, 6);
        List<Integer> numbersList = new ArrayList<>(numbers);
        
        System.out.println("Original list: " + numbersList);
        
        // Sorting
        Collections.sort(numbersList);
        System.out.println("Sorted: " + numbersList);
        
        // Reversing
        Collections.reverse(numbersList);
        System.out.println("Reversed: " + numbersList);
        
        // Shuffling
        Collections.shuffle(numbersList);
        System.out.println("Shuffled: " + numbersList);
        
        // Sublist
        List<Integer> subList = numbersList.subList(2, 5);
        System.out.println("Sublist (2-5): " + subList);
        
        // Conversion to array
        Integer[] array = numbersList.toArray(new Integer[0]);
        System.out.println("Array: " + Arrays.toString(array));
        
        // List iteration methods
        System.out.println("\nIteration Methods:");
        System.out.print("For loop: ");
        for (int i = 0; i < numbersList.size(); i++) {
            System.out.print(numbersList.get(i) + " ");
        }
        
        System.out.print("\nEnhanced for: ");
        for (int num : numbersList) {
            System.out.print(num + " ");
        }
        
        System.out.print("\nIterator: ");
        Iterator<Integer> iterator = numbersList.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        
        System.out.print("\nForEach: ");
        numbersList.forEach(num -> System.out.print(num + " "));
        
        System.out.println("\n");
    }
    
    // 6. List with Custom Objects
    private static void customObjectListDemo() {
        System.out.println("6. LIST WITH CUSTOM OBJECTS");
        System.out.println("===========================");
        
        List<Student> students = new ArrayList<>();
        students.add(new Student(103, "Alice", 3.8));
        students.add(new Student(101, "Bob", 3.5));
        students.add(new Student(105, "Charlie", 3.9));
        students.add(new Student(102, "Diana", 3.7));
        students.add(new Student(104, "Eve", 3.6));
        
        System.out.println("Original student list:");
        students.forEach(System.out::println);
        
        // Sorting by ID
        Collections.sort(students, (s1, s2) -> s1.id - s2.id);
        System.out.println("\nSorted by ID:");
        students.forEach(System.out::println);
        
        // Sorting by name
        Collections.sort(students, (s1, s2) -> s1.name.compareTo(s2.name));
        System.out.println("\nSorted by Name:");
        students.forEach(System.out::println);
        
        // Sorting by GPA (descending)
        Collections.sort(students, (s1, s2) -> Double.compare(s2.gpa, s1.gpa));
        System.out.println("\nSorted by GPA (descending):");
        students.forEach(System.out::println);
        
        System.out.println();
    }
    
    // 7. Java 8+ Features with Lists
    private static void java8FeaturesDemo() {
        System.out.println("7. JAVA 8+ FEATURES");
        System.out.println("====================");
        
        List<String> languages = Arrays.asList("Java", "Python", "JavaScript", "C++", "Kotlin", "Swift", "Go");
        
        System.out.println("Original list: " + languages);
        
        // Filtering
        List<String> filtered = languages.stream()
            .filter(lang -> lang.startsWith("J"))
            .collect(Collectors.toList());
        System.out.println("Languages starting with 'J': " + filtered);
        
        // Mapping
        List<Integer> lengths = languages.stream()
            .map(String::length)
            .collect(Collectors.toList());
        System.out.println("Lengths: " + lengths);
        
        // Sorting
        List<String> sorted = languages.stream()
            .sorted()
            .collect(Collectors.toList());
        System.out.println("Sorted: " + sorted);
        
        // Finding
        Optional<String> first = languages.stream()
            .filter(lang -> lang.length() > 5)
            .findFirst();
        System.out.println("First language with length > 5: " + first.orElse("None"));
        
        // Statistics
        IntSummaryStatistics stats = languages.stream()
            .mapToInt(String::length)
            .summaryStatistics();
        System.out.println("Length statistics - Max: " + stats.getMax() + 
                          ", Min: " + stats.getMin() + 
                          ", Average: " + stats.getAverage());
        
        // Grouping
        Map<Integer, List<String>> groupedByLength = languages.stream()
            .collect(Collectors.groupingBy(String::length));
        System.out.println("Grouped by length: " + groupedByLength);
    }
    
    // Custom class for demonstration
    static class Student {
        int id;
        String name;
        double gpa;
        
        Student(int id, String name, double gpa) {
            this.id = id;
            this.name = name;
            this.gpa = gpa;
        }
        
        @Override
        public String toString() {
            return String.format("Student{id=%d, name='%s', gpa=%.2f}", id, name, gpa);
        }
    }
}