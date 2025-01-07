package Model;

import java.util.LinkedList;
import java.util.Queue;

public class QueueOfCustomers {
    private Queue<Customer> customers;

    public QueueOfCustomers() {
        this.customers = new LinkedList<>();
    }

    public void enqueue(Customer customer) {
        customers.add(customer);
    }

    public Customer dequeue() {
        return customers.poll();
    }

    public void addCustomer(Customer customer) {
        enqueue(customer);
    }

    public boolean removeCustomer(int queueNumber) {
        return customers.removeIf(customer -> customer.getQueueNumber() == queueNumber);
    }

    public int getNextQueueNumber() {
        return customers.size() + 1; // Assuming queue numbers start from 1
    }

    public boolean isEmpty() {
        return customers.isEmpty();
    }

    public int getSize() {
        return customers.size(); // Return the size of the queue
    }

    // New method to get the list of customers
    public Queue<Customer> getCustomers() {
        return customers; // Return the queue of customers
    }

    // New method to find a customer by name
    public Customer findCustomerByName(String name) {
        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(name)) {
                return customer;
            }
        }
        return null; // Return null if no matching customer is found
    }
}