package service.impl;

import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import repository.ICustomerRepository;
import service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    public ICustomerRepository iCustomerRepository;

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return iCustomerRepository.findAll(pageable);
    }

    @Override
    public Customer findById(int id) {
        return iCustomerRepository.findOne(id);
    }

    @Override
    public void save(Customer customer) {
        iCustomerRepository.save(customer);
    }

    @Override
    public void remove(int id) {
        iCustomerRepository.delete(id);
    }

    @Override
    public Page<Customer> findAllByLastNameContaining(String name, Pageable pageable) {
        return iCustomerRepository.findAllByNameContaining(name, pageable);
    }
}
