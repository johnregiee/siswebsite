package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAccountsByStudentId(Long studentId) {
        return accountRepository.findByStudentId(studentId);
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }
}
