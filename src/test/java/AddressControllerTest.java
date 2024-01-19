import com.example.api.domain.Address;
import com.example.api.domain.dto.AddressDTO;
import com.example.api.service.AddressServiceImpl;
import com.example.api.web.rest.AddressController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Mock
    private AddressServiceImpl addressService;

    @InjectMocks
    private AddressController controller;

    @Test
    void testFindAll() {
        Integer mockPage = 0;
        Integer mockSize = 10;
        String mockCidade = "Mock City";
        String mockEstado = "Mock State";
        String mockSort = "sort";
        List<Address> mockAddressList = new ArrayList<>();
        when(addressService.findAll(any(), any())).thenReturn(new PageImpl<>(mockAddressList));
        ResponseEntity<?> result = controller.findAll(mockPage, mockSize, mockCidade, mockEstado, mockSort);
        verify(addressService, times(1)).findAll(any(), any());
        assertNotNull(result);
        assertEquals(ResponseEntity.noContent().build(), result);
    }

    @Test
    void testFindById() {
        Long mockId = 1L;
        AddressDTO mockAddressDTO = new AddressDTO();
        when(addressService.findById(mockId)).thenReturn(mockAddressDTO);
        ResponseEntity<?> result = controller.findById(mockId);
        verify(addressService, times(1)).findById(mockId);
        assertNotNull(result);
        assertEquals(ResponseEntity.ok(mockAddressDTO), result);
    }

    @Test
    void testSave() {
        AddressDTO mockAddressDTO = new AddressDTO();
        AddressDTO mockSavedAddressDTO = new AddressDTO();
        when(addressService.save(mockAddressDTO)).thenReturn(mockSavedAddressDTO);
        ResponseEntity<?> result = controller.save(mockAddressDTO);
        verify(addressService, times(1)).save(mockAddressDTO);
        assertNotNull(result);
        assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(mockSavedAddressDTO), result);
    }

    @Test
    void testEdit() {
        AddressDTO mockAddressDTO = new AddressDTO();
        AddressDTO mockEditedAddressDTO = new AddressDTO();
        when(addressService.edit(mockAddressDTO)).thenReturn(mockEditedAddressDTO);
        ResponseEntity<?> result = controller.edit(mockAddressDTO);
        verify(addressService, times(1)).edit(mockAddressDTO);
        assertNotNull(result);
        assertEquals(ResponseEntity.status(HttpStatus.OK).body(mockEditedAddressDTO), result);
    }

    @Test
    void testDelete() {
        Long mockId = 1L;
        ResponseEntity<?> result = controller.delete(mockId);
        verify(addressService, times(1)).delete(mockId);
        assertNotNull(result);
        assertEquals(ResponseEntity.ok().build(), result);
    }
}
