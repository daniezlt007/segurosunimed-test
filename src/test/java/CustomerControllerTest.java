import com.example.api.domain.Customer;
import com.example.api.domain.dto.CustomerDTO;
import com.example.api.service.CustomerService;
import com.example.api.service.CustomerServiceImpl;
import com.example.api.web.rest.CustomerController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerServiceImpl service;

    @InjectMocks
    private CustomerController controller;

    @Test
    void testFindAll() {
        // Mock parameters
        Integer mockPage = 0;
        Integer mockSize = 10;
        String mockName = "Homem Aranha";
        String mockEmail = "aranha@vingadores.com";
        String mockGender = "M";
        String mockSort = "name";

        when(service.findAll(any(), any())).thenReturn(Page.empty());
        ResponseEntity<?> result = controller.findAll(mockPage, mockSize, mockName, mockEmail, mockGender, mockSort);
        verify(service, times(1)).findAll(any(), any());
        assertNotNull(result);
        assertEquals(ResponseEntity.noContent().build(), result);
    }

    @Test
    void testFindById() {
        Long mockId = 1L;
        Customer mockCustomer = new Customer();
        when(service.findById(mockId)).thenReturn(mockCustomer);
        ResponseEntity<?> result = controller.findById(mockId);
        verify(service, times(1)).findById(mockId);
        assertNotNull(result);
        assertEquals(ResponseEntity.ok(mockCustomer), result);
    }

    @Test
    void testSave() {
        CustomerDTO mockCustomerDTO = new CustomerDTO();
        Customer mockCustomer = new Customer();
        when(service.save(mockCustomerDTO)).thenReturn(mockCustomer);
        ResponseEntity<?> result = controller.save(mockCustomerDTO);
        verify(service, times(1)).save(mockCustomerDTO);
        assertNotNull(result);
        assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(mockCustomer), result);
    }

    @Test
    void testEdit() {
        CustomerDTO mockCustomerDTO = new CustomerDTO();
        Customer mockCustomer = new Customer();
        when(service.edit(mockCustomerDTO)).thenReturn(mockCustomer);
        ResponseEntity<?> result = controller.edit(mockCustomerDTO);
        verify(service, times(1)).edit(mockCustomerDTO);
        assertNotNull(result);
        assertEquals(ResponseEntity.status(HttpStatus.OK).body(mockCustomer), result);
    }

    @Test
    void testDelete() {
        Long mockId = 1L;
        ResponseEntity<?> result = controller.delete(mockId);
        verify(service, times(1)).delete(mockId);
        assertNotNull(result);
        assertEquals(ResponseEntity.ok().build(), result);
    }

}
