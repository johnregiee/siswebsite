package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;

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

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public boolean deleteAccountById(Long id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Account updateAccount(Long id, Account updatedAccount) {
        return accountRepository.findById(id).map(account -> {
            if (updatedAccount.getStudentId() != null) {
                account.setStudentId(updatedAccount.getStudentId());
            }
            if (updatedAccount.getSemester() != null) {
                account.setSemester(updatedAccount.getSemester());
            }
            if (updatedAccount.getTuitionFee() != null) {
                account.setTuitionFee(updatedAccount.getTuitionFee());
            }
            if (updatedAccount.getAmountPaid() != null) {
                account.setAmountPaid(updatedAccount.getAmountPaid());
            }
            // Add other fields as needed
            return accountRepository.save(account);
        }).orElse(null);
    }
}
