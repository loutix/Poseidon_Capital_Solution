package org.ocr.poseidon.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ocr.poseidon.domain.BidList;
import org.ocr.poseidon.exceptions.ItemNotFoundException;
import org.ocr.poseidon.repositories.BidListRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BidListServiceImplTest {

    private BidListServiceImpl bidListService;

    @Mock
    private BidListRepository bidListRepository;

    @BeforeEach
    public void setUp() {
        bidListService = new BidListServiceImpl(bidListRepository);
    }

    @Test
    @DisplayName("getById")
    void getById_existingId() {
        // GIVEN
        Integer id = 1;
        BidList bidList = new BidList();
        bidList.setAccount("test account");
        bidList.setType("test type");
        bidList.setBidQuantity(20.00);

        when(bidListRepository.findById(id)).thenReturn(Optional.of(bidList));

        // WHEN
        BidList result = bidListService.getById(id);

        // THEN
        System.out.println(result);
        assertEquals(bidList, result);
        assertEquals(bidList.getAccount(), result.getAccount());
    }

    @Test
    @DisplayName("getById ItemNotFoundException")
    void getById_No_existingId() {
        // GIVEN
        Integer id = 1;

        when(bidListRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN
        // THEN
        assertThrows(ItemNotFoundException.class, () -> bidListService.getById(id));
    }

    @Test
    @DisplayName("getAll")
    void getAll() {
        // GIVEN
        BidList bidList = new BidList();
        bidList.setAccount("test account");
        bidList.setType("test type");
        bidList.setBidQuantity(11.00);
        bidList.setBidListId(1);

        BidList bidList2 = new BidList();
        bidList2.setAccount("test account2");
        bidList2.setType("test type2");
        bidList2.setBidQuantity(22.00);
        bidList2.setBidListId(2);

        List<BidList> bidListList = List.of(bidList, bidList2);

        when(bidListRepository.findAll()).thenReturn(bidListList);

        // WHEN
        List<BidList> result = bidListService.getAll();

        // THEN
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(bid -> bid.getId().equals(bidList.getId())));
        assertTrue(result.stream().anyMatch(bid -> bid.getId().equals(bidList2.getId())));
    }

    @Test
    @DisplayName("save")
    void save() {
        //GIVEN
        BidList bidList = new BidList();
        bidList.setAccount("test account");
        bidList.setType("test type");
        bidList.setBidQuantity(11.00);
        bidList.setBidListId(1);

        when(bidListRepository.existsById(anyInt())).thenReturn(false);
        when(bidListRepository.save(bidList)).thenReturn(bidList);

        //WHEN
        BidList result = bidListService.save(bidList);

        //THEN
        verify(bidListRepository, times(1)).existsById(anyInt());
        verify(bidListRepository, times(1)).save(bidList);
        assertEquals(bidList, result);
    }

    @Test
    @DisplayName("save throw error")
    void save_throw_error() {
        //GIVEN
        BidList bidList = new BidList();
        bidList.setAccount("test account");
        bidList.setType("test type");
        bidList.setBidQuantity(11.00);
        bidList.setBidListId(1);

        //WHEN
        when(bidListRepository.existsById(anyInt())).thenReturn(true);

        //THEN
        assertThrows(RuntimeException.class, () -> bidListService.save(bidList));
        verify(bidListRepository, times(0)).save(bidList);
    }

    @Test
    @DisplayName("delete")
    void delete() {
        //GIVEN
        BidList bidList = new BidList();
        bidList.setAccount("test account");
        bidList.setType("test type");
        bidList.setBidQuantity(11.00);
        bidList.setBidListId(1);

        //WHEN
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(bidList));

        bidListService.delete(anyInt());

        //THEN
        verify(bidListRepository,times(1)).findById(anyInt());
        verify(bidListRepository,times(1)).deleteById(anyInt());

    }

    @Test
    @DisplayName("delete throw")
    void delete_throw() {
        //GIVEN
        BidList bidList = new BidList();
        bidList.setAccount("test account");
        bidList.setType("test type");
        bidList.setBidQuantity(11.00);
        bidList.setBidListId(1);

        //WHEN
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.empty());

        //THEN
        assertThrows(ItemNotFoundException.class, () -> bidListService.delete(anyInt()));
        verify(bidListRepository,times(1)).findById(anyInt());
        verify(bidListRepository,times(0)).deleteById(anyInt());

    }

    @Test
    @DisplayName("update")
    void update() {
        //GIVEN
        BidList bidList = new BidList();
        bidList.setAccount("test account");
        bidList.setType("test type");
        bidList.setBidQuantity(11.00);
        bidList.setBidListId(1);

        //WHEN
        when(bidListRepository.existsById(anyInt())).thenReturn(true);
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(bidList));
        when(bidListRepository.save(bidList)).thenReturn(bidList);

        bidListService.update(bidList);
        //THEN
        verify(bidListRepository,times(1)).existsById(anyInt());
        verify(bidListRepository,times(1)).findById(anyInt());
        verify(bidListRepository,times(1)).save(bidList);

    }

    @Test
    @DisplayName("update error")
    void update_error() {
        //GIVEN
        BidList bidList = new BidList();
        bidList.setAccount("test account");
        bidList.setType("test type");
        bidList.setBidQuantity(11.00);
        bidList.setBidListId(1);

        //WHEN
        when(bidListRepository.existsById(anyInt())).thenReturn(false);

        //THEN
        assertThrows(ItemNotFoundException.class, () -> bidListService.update(bidList));

        verify(bidListRepository,times(1)).existsById(anyInt());
        verify(bidListRepository,times(0)).findById(anyInt());
        verify(bidListRepository,times(0)).save(bidList);

    }

    @Test
    @DisplayName("update error 2")
    void update_error_2() {
        //GIVEN
        BidList bidList = new BidList();
        bidList.setAccount("test account");
        bidList.setType("test type");
        bidList.setBidQuantity(11.00);
        bidList.setBidListId(1);

        //WHEN
        when(bidListRepository.existsById(anyInt())).thenReturn(true);
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.empty());

        //THEN
        assertThrows(ItemNotFoundException.class, () -> bidListService.update(bidList));

        verify(bidListRepository,times(1)).existsById(anyInt());
        verify(bidListRepository,times(1)).findById(anyInt());
        verify(bidListRepository,times(0)).save(bidList);

    }


}