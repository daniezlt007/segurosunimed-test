import com.example.api.domain.Customer;
import com.example.api.domain.dto.AddressDTO;
import com.example.api.domain.dto.CustomerDTO;
import com.example.api.exceptions.BussinessException;
import com.example.api.exceptions.ResourceNotFoundException;
import com.example.api.repository.CustomerRepository;
import com.example.api.service.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void testFindAllWithResults() {
        // Mock data
        Specification<Customer> mockSpecification = mock(Specification.class);
        Pageable mockPageable = mock(Pageable.class);

        List<Customer> mockCustomers = new ArrayList<>();
        mockCustomers.add(new Customer(1L, "Customer 1", "customer1@example.com", "M",null));
        mockCustomers.add(new Customer(2L, "Customer 2", "customer2@example.com", "F",null));

        when(repository.findAll(mockSpecification, mockPageable)).thenReturn(new PageImpl<>(mockCustomers));

        Page<Customer> result = customerService.findAll(mockSpecification, mockPageable);

        verify(repository, times(1)).findAll(mockSpecification, mockPageable);

        assertNotNull(result);

        assertEquals(mockCustomers.size(), result.getContent().size());
        assertEquals(mockCustomers.get(0).getName(), result.getContent().get(0).getName());
        assertEquals(mockCustomers.get(1).getName(), result.getContent().get(1).getName());
    }

    @Test
    void testFindAllWithEmptyResult() {
        // Mock data
        Specification<Customer> mockSpecification = mock(Specification.class);
        Pageable mockPageable = mock(Pageable.class);

        // Mock repository.findAll returning an empty page
        when(repository.findAll(mockSpecification, mockPageable)).thenReturn(Page.empty());

        // Call the method
        Page<Customer> result = customerService.findAll(mockSpecification, mockPageable);

        // Verify repository.findAll was called with the correct parameters
        verify(repository, times(1)).findAll(mockSpecification, mockPageable);

        // Verify that the result is not null and is empty
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindById() {
        Long customerId = 1L;
        Customer mockCustomer = new Customer();
        mockCustomer.setId(customerId);
        mockCustomer.setName("Mock Customer");
        mockCustomer.setEmail("mock@example.com");
        mockCustomer.setGender("M");
        when(repository.findById(customerId)).thenReturn(Optional.of(mockCustomer));
        Customer result = customerService.findById(customerId);
        verify(repository, times(1)).findById(customerId);
        assertNotNull(result);
        assertEquals(mockCustomer.getName(), result.getName());
        assertEquals(mockCustomer.getEmail(), result.getEmail());
        assertEquals(mockCustomer.getGender(), result.getGender());
    }

    @Test
    void testFindByIdWithNonExistingCustomer() {
        // Mock data
        Long customerId = 1L;
        when(repository.findById(customerId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerService.findById(customerId));
        verify(repository, times(1)).findById(customerId);
    }

    @Test
    void testFindByIdWithNullInput() {
        assertThrows(BussinessException.class, () -> customerService.findById(null));
    }

    @Test
    void testSave() {
        // Mock data
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Mock Name");
        customerDTO.setEmail("mock@example.com");
        customerDTO.setGender("M");

        List<AddressDTO> addressDTO = new ArrayList<>();
        addressDTO.add(new AddressDTO(1L,"66821000","teste","teste","teste","teste","PA","1234","123","91","1234"));
        customerDTO.setAddressDTOS(addressDTO);
        Customer customerToSave = new Customer(customerDTO);
        when(repository.save(any(Customer.class))).thenReturn(customerToSave);

        Customer result = customerService.save(customerDTO);

        verify(repository, times(1)).save(any(Customer.class));

        assertNotNull(result);

        assertEquals(customerDTO.getName(), result.getName());
        assertEquals(customerDTO.getEmail(), result.getEmail());
        assertEquals(customerDTO.getGender(), result.getGender());
    }

    @Test
    void testSaveWithNullInput() {
        assertThrows(BussinessException.class, () -> customerService.save(null));
    }

    @Test
    void testEdit() {
        // Mock data
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("Mock Name");
        customerDTO.setEmail("mock@example.com");
        customerDTO.setGender("M");

        Customer existingCustomer = new Customer();
        existingCustomer.setId(customerDTO.getId());
        when(repository.findById(customerDTO.getId())).thenReturn(Optional.of(existingCustomer));
        when(repository.save(any(Customer.class))).thenReturn(existingCustomer);
        Customer result = customerService.edit(customerDTO);
        verify(repository, times(1)).findById(customerDTO.getId());
        verify(repository, times(1)).save(any(Customer.class));
        assertNotNull(result);
        assertEquals(customerDTO.getName(), result.getName());
        assertEquals(customerDTO.getEmail(), result.getEmail());
        assertEquals(customerDTO.getGender(), result.getGender());
    }

    @Test
    void testEditWithNonExistingCustomer() {
        // Mock data
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);

        when(repository.findById(customerDTO.getId())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerService.edit(customerDTO));
        verify(repository, times(1)).findById(customerDTO.getId());
        verify(repository, never()).save(any(Customer.class));
    }

    @Test
    void testEditWithNullInput() {
        assertThrows(BussinessException.class, () -> customerService.edit(null));
        verify(repository, never()).findById(anyLong());
        verify(repository, never()).save(any(Customer.class));
    }

}
