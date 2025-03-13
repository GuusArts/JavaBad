package com.example.badapp.service;

import com.example.badapp.model.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class OrderService {

    private List<Order> orders = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();

    public void processOrder(Order order) {
        lock.lock();
        try {
            orders.add(order);
        } finally {
            lock.unlock();
        }
    }

    public List<Order> getOrdersForUser(int userId) {
        List<Order> userOrders = new ArrayList<>();
        lock.lock();
        try {
            for (Order order : orders) {
                if (order.getUserId() == userId) {
                    userOrders.add(order);
                }
            }
        } finally {
            lock.unlock();
        }
        return userOrders;
    }

    public synchronized void updateOrderStatus(int orderId, String status) {
        synchronized (orders) {
            lock.lock();
            try {
                for (Order order : orders) {
                    if (order.getId() == orderId) {
                        order.setStatus(status);
                        break;
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }
}