package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CustomerMapperTest {

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    private static final Long ID = 1L;
    private static final String FIRSTNAME = "lastname";
    private static final String LASTNAME = "lastname";

    @Test
    public void customerToCustomerDto() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRSTNAME);
        customer.setLastName(LASTNAME);

        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);

        Assertions.assertEquals(customer.getId(), customerDTO.getId());
        Assertions.assertEquals(customer.getFirstName(), customerDTO.getFirstName());
        Assertions.assertEquals(customer.getLastName(), customerDTO.getLastName());
    }

    @Test
    public void customerDtoToCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID);
        customerDTO.setFirstName(FIRSTNAME);
        customerDTO.setLastName(LASTNAME);

        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);

        Assertions.assertEquals(customerDTO.getId(), customer.getId());
        Assertions.assertEquals(customerDTO.getFirstName(), customer.getFirstName());
        Assertions.assertEquals(customerDTO.getLastName(), customer.getLastName());
    }
}