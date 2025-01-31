package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerByLastName(String lastname) {
        return customerMapper.customerToCustomerDto(customerRepository.getCustomerByLastName(lastname));
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return customerMapper.customerToCustomerDto(customer);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        return customerMapper.customerToCustomerDto(customerRepository.save(
                customerMapper.customerDtoToCustomer(customerDTO)));
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);

        return customerMapper.customerToCustomerDto(customerRepository.save(customer));
    }

    @Override
    @Transactional
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        if (customerDTO.getFirstName() != null) {
            customer.setFirstName(customerDTO.getFirstName());
        }

        if (customerDTO.getLastName() != null) {
            customer.setLastName(customerDTO.getLastName());
        }

        return customerMapper.customerToCustomerDto(customerRepository.save(customer));
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
