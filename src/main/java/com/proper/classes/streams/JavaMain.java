package com.proper.classes.streams;

import org.apache.logging.log4j.util.PropertySource;
import org.apache.logging.log4j.util.Strings;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaMain {
    public static void main(String[] args) {
//        Map<String, String> map = new HashMap<>();
//        map.put("key", "initial value");
//        map.put("keyForBad", "Bad value2");
//        map.put("keyForBad2", "Bad value2");
//        map.putIfAbsent("key", "first value");
//        map.computeIfAbsent("key2", k -> "first value with key " + k);
//        map.compute("keyForBad", (k, v) -> v != null && v.contains("Bad value") ? null : v);
//
//        map.compute("newestKey", (k, v) -> (v == null ? "ddd" : v));
//        map.computeIfAbsent("absentKey", k -> "first value with key " + k);
//        map.computeIfPresent("absentKey", (k, v) -> v + "updated " + k);
//
//        map.compute("newnewkey", (k, v) -> v + "updated " + k);
//
//        map.put("some", "value");
//        map.merge("some2", "newValue", Strings::concat);
//
//        map.putIfAbsent("dmytro", "good");
//        map.putIfAbsent("dmytro", "bad");
//
//        String name = map.getOrDefault("dmytro5", "guest");
//        System.out.println(name);
//
//        System.out.println(map);


//        List<Integer> integers = List.of(3, 5, 6, 7);
//        Integer reduce = integers.stream().reduce(10, (a, b) -> {
//            return a + b;
//        });
//        System.out.println(reduce);
//
//        Integer result = integers.parallelStream().reduce(0, (a, b) -> {
//            return a + b;
//        }, (a1, b1) -> {
//            return a1 + b1;
//        });
//        System.out.println(result);
//
//        List<String> strings = List.of("aaaaa", "bbb", "ccccccccc");
//
//        Optional<String> optionalString = strings.stream().reduce((a, b) -> a.length() >= b.length() ? a : b);
//        System.out.println(optionalString.get());
//
//        List<String> list = List.of("apple", "banana", "cherry");
//        list.stream().reduce((a, b) -> a + ", " + b).ifPresent(System.out::println);
//
//
//
//        List<Item> items = List.of(
//                new Item("A", 10),
//                new Item("B", 20),
//                new Item("C", 30)
//        );
//
//        items.stream().map(item -> item.price). reduce(Integer::sum).ifPresent(System.out::println);

        List<Order> orders = List.of(
                new Order("001", "Alice", List.of(
                        new Product("Book", 12.99),
                        new Product("Pen", 1.99)
                )),
                new Order("002", "Bob", List.of(
                        new Product("Laptop", 999.99),
                        new Product("Mouse", 25.50)
                )),
                new Order("003", "Alice", List.of(
                        new Product("Bag", 49.99)
                )),
                new Order("004", "Eve", List.of()), // empty order
                new Order("005", "Bob", List.of(
                        new Product("Monitor", 199.99),
                        new Product("Cable", 9.99)
                ))
        );

//        double total = orders.stream()
//                .flatMap(order -> order.products().stream())
//                .mapToDouble(Product::price)
//                .sum();
//        System.out.println(total);

//        orders.stream()
//                .flatMap(order -> order.products().stream())
//                .max(Comparator.comparing(Product::price))
//                .ifPresent(System.out::println);
//
//        Map<String, List<Product>> collected = orders.stream()
//                .collect(Collectors.groupingBy(Order::customer,
//                        Collectors.flatMapping(order -> order.products().stream(), Collectors.toList())));
//        System.out.println(collected);
//
//        Map<String, Double> spentByCustomer = orders.stream()
//                .collect(Collectors.groupingBy(
//                        Order::customer,
//                        Collectors.summingDouble(order -> order.products().stream()
//                                .mapToDouble(Product::price).sum())
//                ));

//        spentByCustomer.forEach((customer, total) ->
//                System.out.println(customer + " spent: $" + total));

        Map<String, List<Order>> ordersByCustomer = orders.stream()
                .collect(Collectors.groupingBy(order -> order.customer()));

        System.out.println(ordersByCustomer);

    }
}

record Product(String name, double price) {}

record Order(String orderId, String customer, List<Product> products) {}

class Item {
    String name;
    int price;
    Item(String name, int price) { this.name = name; this.price = price; }
}