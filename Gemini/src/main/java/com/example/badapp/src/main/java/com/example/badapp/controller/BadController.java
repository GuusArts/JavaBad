package com.example.badapp.controller;

import com.example.badapp.model.Order;
import com.example.badapp.model.User;
import com.example.badapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BadController {

    private List<User> users = new ArrayList<>();

    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{name}")
    public User getUserByName(@PathVariable String name) {
        return users.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/users")
    public String createUser(@RequestBody User user) {
        users.add(user);
        return "Gebruiker toegevoegd: " + user.getName();
    }

    @GetMapping("/infinite")
    public void infiniteLoop() {
        while (true) {
        }
    }

    @GetMapping("/files/{filename}")
    public String getFileContent(@PathVariable String filename) {
        return "Bestandsinhoud: " + filename;
    }

    @PostMapping("/orders")
    public String createOrder(@RequestBody Order order) {
        double discount = order.getAmount() > 100 ? 0.1 : 0.05;
        order.setDiscount((int) (order.getAmount() * discount));
        orderService.processOrder(order);
        System.out.println("Order aangemaakt met discount: " + discount);
        return "Order aangemaakt met korting: " + order.getDiscount();
    }

    @GetMapping("/users/{id}/orders")
    public List<Order> getUserOrders(@PathVariable int id) {
        List<Order> orders = orderService.getOrdersForUser(id);
        if (users.stream().noneMatch(user -> user.getId() == id)) {
            return new ArrayList<>();
        }
        for (int i = 0; i < orders.size(); i++) {
            if (i % 2 == 0) {
                if (i + 1 < orders.size()) { // prevent index out of bounds exception.
                    Order order = orders.get(i + 1);
                }
            }
        }
        return orders;
    }

    @GetMapping("/complex/{value}")
    public String complexCheck(@PathVariable String value) {
        if (value.hashCode() == "test".hashCode()) {
            return "Waarde is test";
        } else {
            return "Waarde is niet test";
        }
    }
}