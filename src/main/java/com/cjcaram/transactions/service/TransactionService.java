package com.cjcaram.transactions.service;

import com.cjcaram.transactions.entity.Transaction;
import com.cjcaram.transactions.model.TransactionDto;
import com.cjcaram.transactions.model.TransactionType;
import com.cjcaram.transactions.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    public List<TransactionDto> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDto.class))
                .collect(Collectors.toList());
    }

    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Transaction transaction = modelMapper.map(transactionDto, Transaction.class);
        transaction.setDateTime(LocalDateTime.now());

        transaction = transactionRepository.save(transaction);
        return modelMapper.map(transaction, TransactionDto.class);
    }

    public TransactionDto getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .map(transaction -> modelMapper.map(transaction, TransactionDto.class))
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<TransactionDto> getTransactionByAccountId(Long accountId) {
        List<Transaction> transactions = transactionRepository.findAllByAccountId(accountId);
        return transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDto.class))
                .collect(Collectors.toList());
    }

    public TransactionDto updateTransaction(Long id, TransactionDto transactionDto) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        transaction.setAccountId(transactionDto.getAccountId());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setType(TransactionType.valueOf(transactionDto.getType()));

        transaction = transactionRepository.save(transaction);
        return modelMapper.map(transaction, TransactionDto.class);
    }
}
