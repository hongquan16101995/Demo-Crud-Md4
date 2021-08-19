package formatter;

import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import service.CustomerService;

import java.text.ParseException;
import java.util.Locale;

@Component
public class CustomerFormatter implements Formatter<Customer> {

    private CustomerService customerService;

    @Autowired
    public CustomerFormatter(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public Customer parse(String text, Locale locale) {
        return customerService.findById(Integer.parseInt(text));
    }

    @Override
    public String print(Customer object, Locale locale) {
        return object.getName();
    }
}
