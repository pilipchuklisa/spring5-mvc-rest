package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.domain.Customer;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.repositories.CustomerRepository;
import guru.springframework.model.CustomerDTO;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplTestIT {

    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    private static final Long ID = 1L;
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";

    Customer customer = new Customer();

    @Before
    public void setUp() {

        customer.setId(ID);
        customer.setFirstName(FIRSTNAME);
        customer.setLastName(LASTNAME);
        customerRepository.save(customer);

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    public void patchCustomerUpdateFirstName() {
        String newFirstName = "new value";

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(newFirstName);

        CustomerDTO patchedCustomerDTO = customerService.patchCustomer(ID, customerDTO);

        Assertions.assertEquals(newFirstName, patchedCustomerDTO.getFirstname());
        Assertions.assertEquals(customer.getLastName(), patchedCustomerDTO.getLastname());
    }

    @Ignore
    @Test
    public void patchCustomerNotFound() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("new value");

        Assertions.assertThrows(ResourceNotFoundException.class, () -> customerService.patchCustomer(ID, customerDTO));
    }

    @Ignore
    @Test
    public void patchCustomerUpdateLastName() {
        String newLastName = "new value";

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(newLastName);

        CustomerDTO patchedCustomerDTO = customerService.patchCustomer(ID, customerDTO);

        Assertions.assertEquals(customer.getFirstName(), patchedCustomerDTO.getFirstname());
        Assertions.assertEquals(newLastName, patchedCustomerDTO.getLastname());
    }
}