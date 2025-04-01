package com.cjcaram.transactions.controller;

import com.cjcaram.transactions.model.TransactionDto;
import com.cjcaram.transactions.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Transaction", description = "API used to manage transactions")
@RestController
@RequestMapping("api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "List all transactions")
    @GetMapping
    public List<TransactionDto> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @Operation(summary = "Add new transaction")
    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody @Valid TransactionDto transactionDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransaction(transactionDto));
    }

    @Operation(summary = "Get transaction by id")
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getTransactionById(id));
    }

    @Operation(summary = "Get transaction by account id")
    @GetMapping("/account/{accountID}")
    public ResponseEntity<List<TransactionDto>> getTransactionByAccountIdId(@PathVariable Long accountID) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.getTransactionByAccountId(accountID));
    }

    // TODO: PathMapping

    @Operation(summary = "Update transaction")
    @PutMapping("/{id}")
    public ResponseEntity<TransactionDto> updateTransaction(
            @PathVariable Long id, @RequestBody @Valid TransactionDto transactionDto) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, transactionDto));
    }

    // TRANSACTION SHOULD NOT BE DELETED, IT COULD BREAK DE ACCOUNT BALANCE
}

