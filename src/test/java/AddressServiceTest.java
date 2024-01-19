import com.example.api.domain.Address;
import com.example.api.domain.dto.AddressDTO;
import com.example.api.exceptions.BussinessException;
import com.example.api.exceptions.ResourceNotFoundException;
import com.example.api.repository.AddressRepository;
import com.example.api.service.AddressService;
import com.example.api.service.AddressServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    private AddressRepository repository;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    public void testFindAll() {

        Specification<Address> spec = Specification.where(null);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("cidade"));

        Address address = new Address();
        address.setId(1l);
        address.setCep("66821000");

        List<Address> addressList = new ArrayList<>();
        addressList.add(address);

        Page<Address> page = new PageImpl<>(addressList);

        when(repository.findAll(spec, pageable)).thenReturn(page);

        Page<Address> result = addressService.findAll(spec, pageable);
        assertEquals(addressList.size(), result.getContent().size());

    }

    @Test
    public void testFindAllWithEmptyList() {

        Specification<Address> spec = Specification.where(null);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("cidade"));
        List<Address> emptyList = new ArrayList<>();

        Page<Address> page = new PageImpl<>(emptyList);

        when(repository.findAll(spec, pageable)).thenReturn(page);

        Page<Address> result = addressService.findAll(spec, pageable);

        assertEquals(emptyList.size(), result.getContent().size());


    }

    @Test
    public void testFindById() {
        Address mockAddress = new Address();
        mockAddress.setId(1L);
        mockAddress.setLogradouro("Mock Street");
        mockAddress.setLocalidade("Mock City");

        when(repository.findById(1L)).thenReturn(Optional.of(mockAddress));

        AddressDTO result = addressService.findById(1L);

        assertNotNull(result);
        assertEquals(mockAddress.getId(), result.getId());
        assertEquals(mockAddress.getLogradouro(), result.getLogradouro());
        assertEquals(mockAddress.getLocalidade(), result.getLocalidade());
    }

    @Test
    public void testFindNonExistingAddress() {
        assertThrows(BussinessException.class, () -> {
            addressService.findById(null);
        });
    }


    @Test
    void testSave() {
        // Mock data
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setLogradouro("Mock Street");
        addressDTO.setLocalidade("Mock City");

        Address savedAddress = new Address(addressDTO);
        when(repository.save(any(Address.class))).thenReturn(savedAddress);

        AddressDTO result = addressService.save(addressDTO);

        verify(repository, times(1)).save(any(Address.class));

        assertNotNull(result);

        assertEquals(addressDTO.getLogradouro(), result.getLogradouro());
        assertEquals(addressDTO.getLocalidade(), result.getLocalidade());
    }

    @Test
    void testSaveWithNullInput() {
        assertThrows(BussinessException.class, () -> addressService.save(null));
    }

    @Test
    void testSaveWithNullResult() {
        // Mock data
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setLogradouro("Mock Street");
        addressDTO.setLocalidade("Mock City");

        // Mock repository.save returning null
        when(repository.save(any(Address.class))).thenReturn(null);

        // Call the method and expect an exception
        assertThrows(BussinessException.class, () -> addressService.save(addressDTO));
    }

    @Test
    void testEdit() {
        // Mock data
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(1L);
        addressDTO.setLogradouro("Mock Street");
        addressDTO.setLocalidade("Mock City");

        Address existingAddress = new Address();
        existingAddress.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(existingAddress));

        when(repository.save(any(Address.class))).thenReturn(existingAddress);

        AddressDTO result = addressService.edit(addressDTO);

        verify(repository, times(1)).findById(1L);

        verify(repository, times(1)).save(any(Address.class));

        assertNotNull(result);

        assertEquals(addressDTO.getLogradouro(), result.getLogradouro());
        assertEquals(addressDTO.getLocalidade(), result.getLocalidade());
    }

    @Test
    void testEditWithNullInput() {
        assertThrows(BussinessException.class, () -> addressService.edit(null));

        verify(repository, never()).findById(anyLong());
        verify(repository, never()).save(any(Address.class));
    }

    @Test
    void testEditWithNullId() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setLogradouro("Mock Street");
        addressDTO.setLocalidade("Mock City");

        assertThrows(BussinessException.class, () -> addressService.edit(addressDTO));

        verify(repository, never()).findById(anyLong());
        verify(repository, never()).save(any(Address.class));
    }

    @Test
    void testEditWithNonExistingAddress() {
        // Mock data
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(1L);
        addressDTO.setLogradouro("Mock Street");
        addressDTO.setLocalidade("Mock City");
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addressService.edit(addressDTO));

        verify(repository, times(1)).findById(1L);

        verify(repository, never()).save(any(Address.class));
    }



}
