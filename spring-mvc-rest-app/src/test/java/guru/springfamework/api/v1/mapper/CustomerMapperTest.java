package guru.springfamework.api.v1.mapper;

import guru.springfamework.domain.Customer;
import guru.springframework.model.CustomerDTO;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CustomerMapperTest {

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    private static final Long ID = 1L;
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";

    @Test
    public void customerToCustomerDto() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRSTNAME);
        customer.setLastName(LASTNAME);

        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);

        Assertions.assertEquals(customer.getFirstName(), customerDTO.getFirstname());
        Assertions.assertEquals(customer.getLastName(), customerDTO.getLastname());
    }

    @Test
    public void customerDtoToCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);

        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);

        Assertions.assertEquals(customerDTO.getFirstname(), customer.getFirstName());
        Assertions.assertEquals(customerDTO.getLastname(), customer.getLastName());
    }
}