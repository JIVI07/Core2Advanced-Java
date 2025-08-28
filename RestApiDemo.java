import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.*;
import java.util.*;

@SpringBootApplication
public class RestApiDemo {
    public static void main(String[] args) {
        SpringApplication.run(RestApiDemo.class, args);
    }
}

@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private Integer age;
    
    public User() {}
    
    public User(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
}

interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByNameContainingIgnoreCase(String name);
}

@RestController
@RequestMapping("/api/users")
class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = userOptional.get();
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setAge(userDetails.getAge());
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String name) {
        List<User> users = userRepository.findByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
}

@Entity
@Table(name = "products")
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    @Column(nullable = false)
    private Double price;
    
    private Integer stockQuantity;
    
    public Product() {}
    
    public Product(String name, String description, Double price, Integer stockQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
}

interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPriceLessThanEqual(Double maxPrice);
    List<Product> findByNameContainingIgnoreCase(String name);
}

@RestController
@RequestMapping("/api/products")
class ProductController {
    
    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Product product = productOptional.get();
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStockQuantity(productDetails.getStockQuantity());
        Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(updatedProduct);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/filter/price")
    public ResponseEntity<List<Product>> filterByPrice(@RequestParam Double maxPrice) {
        List<Product> products = productRepository.findByPriceLessThanEqual(maxPrice);
        return ResponseEntity.ok(products);
    }
}

@RestController
@RequestMapping("/api")
class HealthController {
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", new Date().toString());
        response.put("service", "rest-api-demo");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("name", "REST API Demo");
        response.put("version", "1.0.0");
        response.put("description", "A comprehensive REST API demonstration with Spring Boot");
        response.put("endpoints", Arrays.asList(
            "GET /api/health",
            "GET /api/info",
            "GET /api/users",
            "POST /api/users",
            "GET /api/users/{id}",
            "PUT /api/users/{id}",
            "DELETE /api/users/{id}",
            "GET /api/users/search?name={name}",
            "GET /api/users/email/{email}",
            "GET /api/products",
            "POST /api/products",
            "GET /api/products/{id}",
            "PUT /api/products/{id}",
            "DELETE /api/products/{id}",
            "GET /api/products/search?name={name}",
            "GET /api/products/filter/price?maxPrice={price}"
        ));
        return ResponseEntity.ok(response);
    }
}

@RestController
@RequestMapping("/api/calculator")
class CalculatorController {
    
    @GetMapping("/add")
    public ResponseEntity<Map<String, Object>> add(@RequestParam Double a, @RequestParam Double b) {
        Map<String, Object> response = new HashMap<>();
        response.put("operation", "addition");
        response.put("a", a);
        response.put("b", b);
        response.put("result", a + b);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/subtract")
    public ResponseEntity<Map<String, Object>> subtract(@RequestParam Double a, @RequestParam Double b) {
        Map<String, Object> response = new HashMap<>();
        response.put("operation", "subtraction");
        response.put("a", a);
        response.put("b", b);
        response.put("result", a - b);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/multiply")
    public ResponseEntity<Map<String, Object>> multiply(@RequestParam Double a, @RequestParam Double b) {
        Map<String, Object> response = new HashMap<>();
        response.put("operation", "multiplication");
        response.put("a", a);
        response.put("b", b);
        response.put("result", a * b);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/divide")
    public ResponseEntity<Map<String, Object>> divide(@RequestParam Double a, @RequestParam Double b) {
        if (b == 0) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Division by zero is not allowed");
            return ResponseEntity.badRequest().body(error);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("operation", "division");
        response.put("a", a);
        response.put("b", b);
        response.put("result", a / b);
        return ResponseEntity.ok(response);
    }
}