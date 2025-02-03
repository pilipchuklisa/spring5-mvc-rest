package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.domain.Customer;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.repositories.CustomerRepository;
import guru.springframework.model.CustomerDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerServiceImplTest {

    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    private static final Long ID = 1L;
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    public void getAllCustomers() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRSTNAME);
        customer.setLastName(LASTNAME);

        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        Mockito.when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        Assertions.assertEquals(customers.size(), customerDTOS.size());
    }

    @Test
    public void getCustomerByLastName() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRSTNAME);
        customer.setLastName(LASTNAME);

        Mockito.when(customerRepository.getCustomerByLastName(LASTNAME)).thenReturn(customer);

        CustomerDTO customerDTO = customerService.getCustomerByLastName(LASTNAME);

        Assertions.assertEquals(FIRSTNAME, customerDTO.getFirstname());
        Assertions.assertEquals(LASTNAME, customerDTO.getLastname());
    }

    @Test
    public void getCustomerById() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRSTNAME);
        customer.setLastName(LASTNAME);

        Mockito.when(customerRepository.findById(ID)).thenReturn(Optional.of(customer));

        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        Assertions.assertEquals(FIRSTNAME, customerDTO.getFirstname());
        Assertions.assertEquals(LASTNAME, customerDTO.getLastname());
    }

    @Test
    public void getCustomerByIdNotFound() {

        Mockito.when(customerRepository.findById(ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(ID));
    }

    @Test
    public void createNewCustomer() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRSTNAME);
        customer.setLastName(LASTNAME);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);

        Mockito.when(customerRepository.save(Mockito.any())).thenReturn(customer);

        CustomerDTO savedCustomerDTO = customerService.createNewCustomer(customerDTO);

        Assertions.assertEquals(FIRSTNAME, savedCustomerDTO.getFirstname());
        Assertions.assertEquals(LASTNAME, savedCustomerDTO.getLastname());
    }

    @Test
    public void updateCustomer() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRSTNAME);
        customer.setLastName(LASTNAME);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);

        Mockito.when(customerRepository.save(customer)).thenReturn(customer);

        CustomerDTO updatedCustomerDTO = customerService.updateCustomer(ID, customerDTO);

        Assertions.assertEquals(FIRSTNAME, updatedCustomerDTO.getFirstname());
        Assertions.assertEquals(LASTNAME, updatedCustomerDTO.getLastname());
    }

    @Test
    public void deleteCustomerById() {
        customerService.deleteCustomerById(ID);

        Mockito.verify(customerRepository, Mockito.times(1)).deleteById(ID);
    }
}