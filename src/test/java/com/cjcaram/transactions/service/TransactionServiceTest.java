package com.cjcaram.transactions.service;

import com.cjcaram.transactions.entity.Transaction;
import com.cjcaram.transactions.model.TransactionDto;
import com.cjcaram.transactions.model.TransactionType;
import com.cjcaram.transactions.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void shouldGetAllTransactions() {
        List<Transaction> transactions = List.of(
                new Transaction(1L, 100L, new BigDecimal("50.0"), TransactionType.DEPOSIT, LocalDateTime.now()),
                new Transaction(2L, 200L, new BigDecimal("100.0"), TransactionType.WITHDRAW, LocalDateTime.now())
        );

        when(transactionRepository.findAll()).thenReturn(transactions);
        when(modelMapper.map(any(Transaction.class), eq(TransactionDto.class)))
                .thenReturn(new TransactionDto(100L, new BigDecimal("50.0"), TransactionType.DEPOSIT.name()));

        List<TransactionDto> result = transactionService.getAllTransactions();

        assertEquals(2, result.size());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void shouldCreateTransaction() {
        TransactionDto dto = new TransactionDto(100L, new BigDecimal("50.0"), TransactionType.DEPOSIT.name());
        Transaction transaction = new Transaction(1L, 100L, new BigDecimal("50.0"), TransactionType.DEPOSIT, LocalDateTime.now());

        when(modelMapper.map(dto, Transaction.class)).thenReturn(transaction);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(modelMapper.map(transaction, TransactionDto.class)).thenReturn(dto);

        TransactionDto result = transactionService.createTransaction(dto);

        assertEquals(new BigDecimal("50.0"), result.getAmount());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void shouldThrowExceptionWhenTransactionNotFound() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> transactionService.updateTransaction(1L, new TransactionDto()));
    }
}
