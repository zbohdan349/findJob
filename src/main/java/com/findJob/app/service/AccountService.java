package com.findJob.app.service;

import com.findJob.app.model.Account;
import com.findJob.app.model.User;
import com.findJob.app.model.dto.RegDto;
import com.findJob.app.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepo accountRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Account>account = accountRepo.findByEmail(s);
        if(account.isPresent())
            return account.get();
        else throw new NullPointerException("Такого користувача немає");
    }

    public void save(RegDto dto){

        User user = new User();

        user.setName(dto.getName());

        user.setSecondName(dto.getSecondName());

        user.setDescription(dto.getDescription());

        Account account =new Account();

        account.setEmail(dto.getEmail());

        account.setRole(dto.getRole());

        account.setPassword("{noop}"+dto.getPassword());

        account.setPhone(dto.getPhone());

        accountRepo.saveAndFlush(account);
    }
}
