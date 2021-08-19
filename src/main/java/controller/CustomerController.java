package controller;

import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.CustomerService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer")
    public ModelAndView showListCustomer(@RequestParam("string") Optional<String> string,
                                         @SortDefault(sort = {"name"}) @PageableDefault(value = 5) Pageable pageable) {
        Page<Customer> customers;
        ModelAndView modelAndView = new ModelAndView("/list");
        if (string.isPresent()) {
            customers = customerService.findAllByLastNameContaining(string.get(), pageable);
        } else {
            customers = customerService.findAll(pageable);
        }
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/create-customer")
    public ModelAndView createCustomer(){
        return new ModelAndView("/create", "customer", new Customer());
    }

    @PostMapping("/create-customer")
    public ModelAndView createCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult,
                                       @SortDefault(sort = {"name"}) @PageableDefault(value = 5) Pageable pageable) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("/create");
            modelAndView.addObject("customer", customer);
            return modelAndView;
        }
        customerService.save(customer);
        Page<Customer> customers = customerService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/list");
        modelAndView.addObject("customers", customers);
        modelAndView.addObject("success", "Tạo thành công!");
        return modelAndView;
    }

    @GetMapping("/edit-customer/{id}")
    public ModelAndView editCustomer(@PathVariable int id) {
        Customer customer = customerService.findById(id);
        return new ModelAndView("/edit", "customer", customer);

    }

    @PostMapping("/edit-customer/{id}")
    public ModelAndView updateCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult,
                                       @SortDefault(sort = {"name"}) @PageableDefault(value = 5) Pageable pageable) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("/edit");
            modelAndView.addObject("customer", customer);
            return modelAndView;
        }
        customerService.save(customer);
        Page<Customer> customers = customerService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/list");
        modelAndView.addObject("customers", customers);
        modelAndView.addObject("success", "Cập nhật thành công!");
        return modelAndView;
    }

    @GetMapping("/delete-customer/{id}")
    public ModelAndView deleteCustomer(@PathVariable int id) {
        Customer customer = customerService.findById(id);
        return new ModelAndView("/delete", "customer", customer);

    }

    @PostMapping("/delete-customer/{id}")
    public ModelAndView removeCustomer(@ModelAttribute("customer") Customer customer,
                                       @SortDefault(sort = {"name"}) @PageableDefault(value = 5) Pageable pageable) {
        customerService.remove(customer.getId());
        Page<Customer> customers = customerService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/list");
        modelAndView.addObject("customers", customers);
        modelAndView.addObject("success", "Cập nhật thành công!");
        return modelAndView;
    }

    @GetMapping("/view-customer/{id}")
    public ModelAndView showCustomer(@PathVariable int id) {
        Customer customer = customerService.findById(id);
        return new ModelAndView("/view", "customer", customer);
    }
}
