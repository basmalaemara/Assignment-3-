package GoodOrderExample;

import java.util.List;

public class GppdOrderExample {
    static class Customer {
        private final String name;
        private final String email;

        Customer(String name, String email) {
            this.name = name;
            this.email = email;
        }

        String getName() { return name; }
        String getEmail() { return email; }
    }

    static class Payment {
        private final String type;

        Payment(String type) { this.type = type; }

        String getType() { return type; }
    }

    static class Order {
        private final String name;
        private final double totalPrice;

        Order(String name, double totalPrice) {
            this.name = name;
            this.totalPrice = totalPrice;
        }

        String getName() { return name; }
        double getTotalPrice() { return totalPrice; }

    }
    interface Ship{
        void ship();
    }
    static class DeliveryOrder extends Order implements Ship {
        DeliveryOrder (String name, double price) {
            super(name, price);
        }
        @Override
        public void ship() {
            System.out.println("Delivering " + getName() + " to customer's address");
        }
    }
     static class PickUpOrder extends Order  {
         PickUpOrder(String name, double price) {
             super(name, price);
         }

    }
    static class PaymentProcessor implements Payer {
        public void processPayment(processPayment card, Order order) {
            System.out.println("Processing payment of order: " + order.getName());
            System.out.println("Issuing payment for amount: " + order.getTotalPrice());
            card.paymentType();
        }
    }
    interface processPayment{
        void paymentType();
    }
    static class processPaymentVISA implements processPayment {
        @Override
        public void paymentType() {
            System.out.println("Processing visa card payments...");
        }
    }
    static class processPaymentMASTERCARD implements processPayment {
        @Override
        public void paymentType() {
            System.out.println("Processing MASTER card payments...");
        }
    }
    static class processPaymentAMERICAN_EXPRESS implements processPayment {
        @Override
        public void paymentType() {
            System.out.println("Processing american express card payments...");
        }
    }
    static class OrderProcessor implements Processor {
        public void processOrder(Order order) {
            System.out.println("Processing order: " + order.getName() + " now...");
        }
    }

    static class EmailNotifier implements Notifier {
        public void sendEmailNotification(Customer customer, String message) {
            System.out.println("Sending email notification to: " + customer.getEmail()
                    + " with message: " + message);
        }
    }

    interface Register {
        void register(String name);
    }
    interface Login {
        void login(String name);
    }
    interface Subscribe{
        void subscribe(String name);
    }
    static class Subscriber implements Register , Subscribe{
        public void register(String name) {
            System.out.println(name + " registered");
        }

        @Override
        public void subscribe(String name) {
            System.out.println(name + " subscribed");
        }
    }
    interface Processor {
        void processOrder(Order order);
    }
    interface Notifier {
        void sendEmailNotification(Customer customer, String message);
    }
    interface Payer {
        void processPayment(processPayment card, Order order);
    }
    static class UserManagement {
        private final Processor processor;
        private final Payer payer;
        private final Notifier notifier;

        UserManagement(Processor processor, Payer payer, Notifier notifier) {
            this.processor = processor;
            this.payer = payer;
            this.notifier = notifier;
        }

        void placeOrder(Customer customer, Order order, processPayment card) {
            processor.processOrder(order);
            payer.processPayment(card, order);
            notifier.sendEmailNotification(customer, "Order " + order.getName() + " confirmed");
        }
    }
    static void shipAll(List<Ship> orders) {
        for (Ship order : orders) {
            order.ship();
        }
    }

    public static void main(String[] args) {
        Customer customer = new Customer("Sara", "sara@example.com");
        Order order = new DeliveryOrder("Laptop", 1200.0);

        UserManagement userManagement = new UserManagement(
                new OrderProcessor(),
                new PaymentProcessor(),
                new EmailNotifier()
        );
        userManagement.placeOrder(customer, order, new processPaymentVISA());

        shipAll(List.of(new DeliveryOrder("Phone", 800.0)));

        Subscriber subscriber = new Subscriber();
        subscriber.register("Omar");
        subscriber.subscribe("Omar");
    }
}


