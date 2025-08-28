import java.util.*;

public class StrategyPattern {

    interface PaymentStrategy {
        void pay(double amount);
    }

    static class CreditCardPayment implements PaymentStrategy {
        private String cardNumber;
        private String name;
        private String cvv;
        private String expiryDate;

        public CreditCardPayment(String cardNumber, String name, String cvv, String expiryDate) {
            this.cardNumber = cardNumber;
            this.name = name;
            this.cvv = cvv;
            this.expiryDate = expiryDate;
        }

        @Override
        public void pay(double amount) {
            System.out.println("Paid " + amount + " using Credit Card: " + cardNumber);
        }
    }

    static class PayPalPayment implements PaymentStrategy {
        private String email;
        private String password;

        public PayPalPayment(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        public void pay(double amount) {
            System.out.println("Paid " + amount + " using PayPal: " + email);
        }
    }

    static class CryptoPayment implements PaymentStrategy {
        private String walletAddress;
        private String cryptoType;

        public CryptoPayment(String walletAddress, String cryptoType) {
            this.walletAddress = walletAddress;
            this.cryptoType = cryptoType;
        }

        @Override
        public void pay(double amount) {
            System.out.println("Paid " + amount + " using " + cryptoType + ": " + walletAddress);
        }
    }

    static class ShoppingCart {
        private List<Item> items = new ArrayList<>();
        private PaymentStrategy paymentStrategy;

        public void addItem(Item item) {
            items.add(item);
        }

        public void removeItem(Item item) {
            items.remove(item);
        }

        public double calculateTotal() {
            return items.stream().mapToDouble(Item::getPrice).sum();
        }

        public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
            this.paymentStrategy = paymentStrategy;
        }

        public void checkout() {
            double total = calculateTotal();
            if (paymentStrategy != null) {
                paymentStrategy.pay(total);
                items.clear();
            } else {
                System.out.println("Please select a payment method first");
            }
        }
    }

    static class Item {
        private String name;
        private double price;

        public Item(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }

    interface CompressionStrategy {
        void compress(String file);
    }

    static class ZipCompression implements CompressionStrategy {
        @Override
        public void compress(String file) {
            System.out.println("Compressing " + file + " using ZIP format");
        }
    }

    static class RarCompression implements CompressionStrategy {
        @Override
        public void compress(String file) {
            System.out.println("Compressing " + file + " using RAR format");
        }
    }

    static class SevenZipCompression implements CompressionStrategy {
        @Override
        public void compress(String file) {
            System.out.println("Compressing " + file + " using 7ZIP format");
        }
    }

    static class CompressionContext {
        private CompressionStrategy strategy;

        public void setCompressionStrategy(CompressionStrategy strategy) {
            this.strategy = strategy;
        }

        public void compressFile(String file) {
            if (strategy != null) {
                strategy.compress(file);
            } else {
                System.out.println("Please select a compression strategy first");
            }
        }
    }

    interface SortingStrategy {
        void sort(int[] array);
    }

    static class BubbleSort implements SortingStrategy {
        @Override
        public void sort(int[] array) {
            System.out.println("Sorting using Bubble Sort");
            for (int i = 0; i < array.length - 1; i++) {
                for (int j = 0; j < array.length - i - 1; j++) {
                    if (array[j] > array[j + 1]) {
                        int temp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                    }
                }
            }
        }
    }

    static class QuickSort implements SortingStrategy {
        @Override
        public void sort(int[] array) {
            System.out.println("Sorting using Quick Sort");
            quickSort(array, 0, array.length - 1);
        }

        private void quickSort(int[] array, int low, int high) {
            if (low < high) {
                int pi = partition(array, low, high);
                quickSort(array, low, pi - 1);
                quickSort(array, pi + 1, high);
            }
        }

        private int partition(int[] array, int low, int high) {
            int pivot = array[high];
            int i = low - 1;
            for (int j = low; j < high; j++) {
                if (array[j] < pivot) {
                    i++;
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
            int temp = array[i + 1];
            array[i + 1] = array[high];
            array[high] = temp;
            return i + 1;
        }
    }

    static class MergeSort implements SortingStrategy {
        @Override
        public void sort(int[] array) {
            System.out.println("Sorting using Merge Sort");
            mergeSort(array, 0, array.length - 1);
        }

        private void mergeSort(int[] array, int left, int right) {
            if (left < right) {
                int mid = left + (right - left) / 2;
                mergeSort(array, left, mid);
                mergeSort(array, mid + 1, right);
                merge(array, left, mid, right);
            }
        }

        private void merge(int[] array, int left, int mid, int right) {
            int n1 = mid - left + 1;
            int n2 = right - mid;

            int[] leftArray = new int[n1];
            int[] rightArray = new int[n2];

            System.arraycopy(array, left, leftArray, 0, n1);
            System.arraycopy(array, mid + 1, rightArray, 0, n2);

            int i = 0, j = 0, k = left;
            while (i < n1 && j < n2) {
                if (leftArray[i] <= rightArray[j]) {
                    array[k] = leftArray[i];
                    i++;
                } else {
                    array[k] = rightArray[j];
                    j++;
                }
                k++;
            }

            while (i < n1) {
                array[k] = leftArray[i];
                i++;
                k++;
            }

            while (j < n2) {
                array[k] = rightArray[j];
                j++;
                k++;
            }
        }
    }

    static class Sorter {
        private SortingStrategy strategy;

        public void setSortingStrategy(SortingStrategy strategy) {
            this.strategy = strategy;
        }

        public void sortArray(int[] array) {
            if (strategy != null) {
                strategy.sort(array);
            } else {
                System.out.println("Please select a sorting strategy first");
            }
        }
    }

    interface DiscountStrategy {
        double applyDiscount(double originalPrice);
    }

    static class NoDiscount implements DiscountStrategy {
        @Override
        public double applyDiscount(double originalPrice) {
            return originalPrice;
        }
    }

    static class PercentageDiscount implements DiscountStrategy {
        private double percentage;

        public PercentageDiscount(double percentage) {
            this.percentage = percentage;
        }

        @Override
        public double applyDiscount(double originalPrice) {
            return originalPrice * (1 - percentage / 100);
        }
    }

    static class FixedAmountDiscount implements DiscountStrategy {
        private double amount;

        public FixedAmountDiscount(double amount) {
            this.amount = amount;
        }

        @Override
        public double applyDiscount(double originalPrice) {
            return Math.max(0, originalPrice - amount);
        }
    }

    static class Product {
        private String name;
        private double price;
        private DiscountStrategy discountStrategy;

        public Product(String name, double price) {
            this.name = name;
            this.price = price;
            this.discountStrategy = new NoDiscount();
        }

        public void setDiscountStrategy(DiscountStrategy discountStrategy) {
            this.discountStrategy = discountStrategy;
        }

        public double getFinalPrice() {
            return discountStrategy.applyDiscount(price);
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }

    static class StrategyPatternDemo {
        public static void main(String[] args) {
            System.out.println("=== Strategy Pattern Demo ===\n");

            ShoppingCart cart = new ShoppingCart();
            cart.addItem(new Item("Laptop", 999.99));
            cart.addItem(new Item("Mouse", 25.50));
            cart.addItem(new Item("Keyboard", 75.00));

            cart.setPaymentStrategy(new CreditCardPayment("1234-5678-9012-3456", "John Doe", "123", "12/25"));
            cart.checkout();
            System.out.println();

            cart.addItem(new Item("Monitor", 299.99));
            cart.setPaymentStrategy(new PayPalPayment("john.doe@example.com", "password123"));
            cart.checkout();
            System.out.println();

            cart.addItem(new Item("Webcam", 89.99));
            cart.setPaymentStrategy(new CryptoPayment("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", "Bitcoin"));
            cart.checkout();
            System.out.println();

            CompressionContext compressor = new CompressionContext();
            compressor.setCompressionStrategy(new ZipCompression());
            compressor.compressFile("document.txt");
            
            compressor.setCompressionStrategy(new RarCompression());
            compressor.compressFile("image.jpg");
            
            compressor.setCompressionStrategy(new SevenZipCompression());
            compressor.compressFile("archive.dat");
            System.out.println();

            int[] numbers = {64, 34, 25, 12, 22, 11, 90};
            Sorter sorter = new Sorter();
            
            sorter.setSortingStrategy(new BubbleSort());
            int[] bubbleSortArray = numbers.clone();
            sorter.sortArray(bubbleSortArray);
            System.out.println("Sorted: " + Arrays.toString(bubbleSortArray));
            
            sorter.setSortingStrategy(new QuickSort());
            int[] quickSortArray = numbers.clone();
            sorter.sortArray(quickSortArray);
            System.out.println("Sorted: " + Arrays.toString(quickSortArray));
            
            sorter.setSortingStrategy(new MergeSort());
            int[] mergeSortArray = numbers.clone();
            sorter.sortArray(mergeSortArray);
            System.out.println("Sorted: " + Arrays.toString(mergeSortArray));
            System.out.println();

            Product product = new Product("Smartphone", 699.99);
            System.out.println("Product: " + product.getName() + ", Original Price: $" + product.getPrice());
            System.out.println("No discount: $" + product.getFinalPrice());
            
            product.setDiscountStrategy(new PercentageDiscount(15));
            System.out.println("15% discount: $" + product.getFinalPrice());
            
            product.setDiscountStrategy(new FixedAmountDiscount(100));
            System.out.println("$100 discount: $" + product.getFinalPrice());
        }
    }
}