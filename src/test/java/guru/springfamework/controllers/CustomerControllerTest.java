package guru.springfamework.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

public class CustomerControllerTest {

    @InjectMocks
    CustomerController customerController;

    @Mock
    CustomerService customerService;

    MockMvc mockMvc;

    private static final Long ID = 1L;
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String API_URL = "/api/v1/customers/";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void getAllCustomers() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID);
        customerDTO.setFirstName(FIRSTNAME);
        customerDTO.setLastName(LASTNAME);

        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customerDTOS.add(customerDTO);

        Mockito.when(customerService.getAllCustomers()).thenReturn(customerDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customers", Matchers.hasSize(1)));
    }

    @Ignore
    @Test
    public void getCustomerNyLastName() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID);
        customerDTO.setFirstName(FIRSTNAME);
        customerDTO.setLastName(LASTNAME);

        Mockito.when(customerService.getCustomerByLastName(LASTNAME)).thenReturn(customerDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + LASTNAME)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.equalTo(LASTNAME)));
    }

    @Test
    public void getCustomerById() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID);
        customerDTO.setFirstName(FIRSTNAME);
        customerDTO.setLastName(LASTNAME);

        Mockito.when(customerService.getCustomerById(ID)).thenReturn(customerDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo((ID.intValue()))));
    }

    @Test
    public void createCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID);
        customerDTO.setFirstName(FIRSTNAME);
        customerDTO.setLastName(LASTNAME);

        Mockito.when(customerService.createNewCustomer(customerDTO)).thenReturn(customerDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(API_URL)
                        .content(new ObjectMapper().writeValueAsString(customerDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("location", API_URL));
    }

    @Test
    public void updateCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID);
        customerDTO.setFirstName(FIRSTNAME);
        customerDTO.setLastName(LASTNAME);

        Mockito.when(customerService.updateCustomer(ID, customerDTO)).thenReturn(customerDTO);

        mockMvc.perform(MockMvcRequestBuilders.put(API_URL + ID)
                        .content(new ObjectMapper().writeValueAsString(customerDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(("$.id"), Matchers.equalTo(ID.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath(("$.firstName"), Matchers.equalTo(FIRSTNAME)))
                .andExpect(MockMvcResultMatchers.jsonPath(("$.lastName"), Matchers.equalTo(LASTNAME)));
    }

    @Test
    public void patchCustomer() throws Exception {
        String newFirstName = "new value";

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID);
        customerDTO.setFirstName(newFirstName);
        customerDTO.setLastName(LASTNAME);

        CustomerDTO customerDTOPatch = new CustomerDTO();
        customerDTOPatch.setFirstName(newFirstName);

        Mockito.when(customerService.patchCustomer(ID, customerDTOPatch)).thenReturn(customerDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch(API_URL + ID)
                        .content(new ObjectMapper().writeValueAsString(customerDTOPatch))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(("$.id"), Matchers.equalTo(ID.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath(("$.firstName"), Matchers.equalTo(newFirstName)))
                .andExpect(MockMvcResultMatchers.jsonPath(("$.lastName"), Matchers.equalTo(LASTNAME)));
    }
}