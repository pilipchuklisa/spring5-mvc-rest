package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
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
    private static final String FIRSTNAME = "lastname";
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

        Assertions.assertEquals(ID, customerDTO.getId());
        Assertions.assertEquals(FIRSTNAME, customerDTO.getFirstName());
        Assertions.assertEquals(LASTNAME, customerDTO.getLastName());
    }

    @Test
    public void getCustomerById() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRSTNAME);
        customer.setLastName(LASTNAME);

        Mockito.when(customerRepository.findById(ID)).thenReturn(Optional.of(customer));

        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        Assertions.assertEquals(ID, customerDTO.getId());
        Assertions.assertEquals(FIRSTNAME, customerDTO.getFirstName());
        Assertions.assertEquals(LASTNAME, customerDTO.getLastName());
    }

    @Test
    public void createNewCustomer() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRSTNAME);
        customer.setLastName(LASTNAME);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID);
        customerDTO.setFirstName(FIRSTNAME);
        customerDTO.setLastName(LASTNAME);

        Mockito.when(customerRepository.save(customer)).thenReturn(customer);

        CustomerDTO savedCustomerDTO = customerService.createNewCustomer(customerDTO);

        Assertions.assertEquals(ID, savedCustomerDTO.getId());
        Assertions.assertEquals(FIRSTNAME, savedCustomerDTO.getFirstName());
        Assertions.assertEquals(LASTNAME, savedCustomerDTO.getLastName());
    }
}