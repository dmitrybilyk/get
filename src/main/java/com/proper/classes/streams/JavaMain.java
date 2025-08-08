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

//        Map<String, List<Order>> ordersByCustomer = orders.stream()
//                .collect(Collectors.groupingBy(Order::customer));

//        Map<String, List<String>> collect = orders.stream()
//                .collect(Collectors.groupingBy(Order::customer,
//                        Collectors.flatMapping(order -> order.products().stream()
//                                        .map(Product::name),
//                                Collectors.toList())));

//        Map<String, Long> collect = orders.stream()
//                .collect(Collectors.groupingBy(Order::customer,
//                        Collectors.flatMapping(order -> order.products().stream(), Collectors.counting())));
//        Map<String, Integer> collect = orders.stream()
//                .collect(Collectors.groupingBy(Order::customer,
//                        Collectors.summingInt(order -> order.products().size())));

//        List<Product> collect = orders.stream()
//                .flatMap(order -> order.products().stream())
//                .toList();

//
//        System.out.println(orders.stream()
//                        .flatMap(order -> order.products().stream()
//                                .map(product -> Map.entry(order.customer(), product)))
//                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.counting())));

//        group products by customer name

//        Map<String, List<Product>> collect = orders.stream()
//                .collect(Collectors.groupingBy(Order::customer,
//                        Collectors.flatMapping(order -> order.products().stream(),
//                                Collectors.toList())));

//        Task: Find the most expensive product each customer has ordered

        Map<String, Optional<Product>> mostExpensiveProductByCustomer = orders.stream()
                .flatMap(order -> order.products().stream()
                        .map(product -> Map.entry(order.customer(), product)))
                .collect(Collectors.groupingBy(
                        stringProductEntry -> {
                            return stringProductEntry.getKey();
                        },
                        Collectors.mapping(
                                stringProductEntry1 -> {
                                    return stringProductEntry1.getValue();
                                },
                                Collectors.maxBy(Comparator.comparingDouble(Product::price))
                        )
                ));

//
//        orders.stream()
//                .flatMap(order -> order.products().stream()
//                        .map(product -> Map.entry(order.customer(), product)))
//                .collect(Collectors.groupingBy(
//                        Map.Entry::getKey,
//                        Collectors.mapping(
//                                Map.Entry::getValue,
//                                Collectors.maxBy(Comparator.comparingDouble(Product::price)))
//                ))
//                .forEach((s, product) -> {
//                    product.ifPresent(product1 ->
//                            System.out.println("Customer - " + s + " : " + product1.price()));
//
//                });

//        Task: Find the first product each customer has ever ordered

//        Map<String, List<Product>> collect = orders.stream()
//                .filter(order -> !order.products().isEmpty())
//                .collect(Collectors.toMap(Order::customer,
//                        Order::products,
//                        (o, o2) -> o
//                ));

//        orders.stream()
//                        .collect(Collectors.groupingBy(order -> order.customer()));

//        Map<String, List<Product>> collect = orders.stream()
//                .flatMap(order -> order.products().stream())
//                .collect(Collectors.groupingBy(product -> product.name()));
//
//        System.out.println(orders.stream()
//                        .flatMap(order -> order.products().stream())
//                                .collect(Collectors.groupingBy(Product::name)));

//        orders.stream()
//                .flatMap(order -> order.products().stream()).distinct().count();

//        group students by language

        List<Student> studentList = Arrays.asList(
                new Student("Doug Lea", Arrays.asList("Java", "C#", "JavaScript")),
                new Student("Bjarne Stroustrup", Arrays.asList("C", "C++", "Java")),
                new Student("Martin Odersky", Arrays.asList("Java", "Scala", "Smalltalk"))
        );
//        studentList.forEach(System.out::println);

        Map<String, List<String>> collect = studentList.stream()
                .flatMap(student -> student.languages.stream()
                        .map(language -> Map.entry(language, student.name)))
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue,
                                Collectors.toList())));

        System.out.println(collect);
    }

    static class Student {
        private String name;
        private List<String> languages;

        Student(String name, List<String> languages) {
            this.name = name;
            this.languages = languages;
        }

        public String getName() {
            return name;
        }

        public List<String> getLanguages() {
            return languages;
        }

        @Override
        public String toString() {
            return this.name + this.languages;
        }
    }

//    // Data example
//    static final List<Student> students = Arrays.asList(
//            new Student("Doug Lea", Arrays.asList("Java", "C#", "JavaScript")),
//            new Student("Bjarne Stroustrup", Arrays.asList("C", "C++", "Java")),
//            new Student("Martin Odersky", Arrays.asList("Java", "Scala", "Smalltalk"))
//    );

}

record Product(String name, double price) {}

record Order(String orderId, String customer, List<Product> products) {}

class Item {
    String name;
    int price;
    Item(String name, int price) { this.name = name; this.price = price; }
}