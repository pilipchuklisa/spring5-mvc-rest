package guru.springfamework.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.services.CustomerService;
import guru.springframework.model.CustomerDTO;
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

        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(RestResponseEntityExceptionHandler.class).build();
    }

    @Test
    public void getAllCustomers() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);

        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customerDTOS.add(customerDTO);

        Mockito.when(customerService.getAllCustomers()).thenReturn(customerDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customers", Matchers.hasSize(1)));
    }

    @Ignore
    @Test
    public void getCustomerNyLastName() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);

        Mockito.when(customerService.getCustomerByLastName(LASTNAME)).thenReturn(customerDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + LASTNAME)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", Matchers.equalTo(LASTNAME)));
    }

    @Test
    public void getCustomerById() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);

        Mockito.when(customerService.getCustomerById(ID)).thenReturn(customerDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Matchers.equalTo(FIRSTNAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", Matchers.equalTo(LASTNAME)));
    }

    @Test
    public void getCustomerByIdNotFound() throws Exception {

        Mockito.when(customerService.getCustomerById(ID)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void createCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);

        Mockito.when(customerService.createNewCustomer(customerDTO)).thenReturn(customerDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(API_URL)
                        .content(new ObjectMapper().writeValueAsString(customerDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void updateCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);

        Mockito.when(customerService.updateCustomer(Mockito.anyLong(), Mockito.any())).thenReturn(customerDTO);

        mockMvc.perform(MockMvcRequestBuilders.put(API_URL + ID)
                        .content(new ObjectMapper().writeValueAsString(customerDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Matchers.equalTo(FIRSTNAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", Matchers.equalTo(LASTNAME)));
    }

    @Test
    public void patchCustomer() throws Exception {
        String newFirstName = "new value";

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(newFirstName);
        customerDTO.setLastname(LASTNAME);

        CustomerDTO customerDTOPatch = new CustomerDTO();
        customerDTOPatch.setFirstname(newFirstName);

        Mockito.when(customerService.patchCustomer(Mockito.anyLong(), Mockito.any())).thenReturn(customerDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch(API_URL + ID)
                        .content(new ObjectMapper().writeValueAsString(customerDTOPatch))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Matchers.equalTo(newFirstName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", Matchers.equalTo(LASTNAME)));
    }

    @Test
    public void deleteCustomer() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL + ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}