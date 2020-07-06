package com.rickydoescode.demo.controller;

import com.rickydoescode.demo.CustomerRepository;
import com.rickydoescode.demo.domain.Customer;
import com.rickydoescode.demo.domain.NewCustomer;
import com.rickydoescode.demo.error.CustomerNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CustomerController {

    public static final String GREETING_MESSAGE = "Hello, World";
    private final CustomerRepository repository;

    @RequestMapping("/")
    public @ResponseBody String greeting() {
        return GREETING_MESSAGE;
    }

    @GetMapping("/customers")
    List<Customer> all() {
        return repository.findAll();
    }

    @PostMapping("/customers")
    Customer newCustomer(@RequestBody NewCustomer newCustomer) {
        return repository.save(new Customer(newCustomer));
    }

    @GetMapping("/customers/{id}")
    Customer get(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @PutMapping("/customers/{id}")
    Customer updateCustomer(@RequestBody NewCustomer newCustomer, @PathVariable Long id) {
        return repository.findById(id)
                .map(customer -> {
                    customer.setFirstName(newCustomer.getFirstName());
                    customer.setLastName(newCustomer.getLastName());
                    return repository.save(customer);
                })
                .orElseGet(() -> {
                    Customer customer = new Customer(newCustomer);
                    customer.setId(id);
                    return repository.save(customer);
                });
    }

    @DeleteMapping("/customers/{id}")
    void deleteCustomer(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
