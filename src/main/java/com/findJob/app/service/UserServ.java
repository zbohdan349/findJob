package com.findJob.app.service;

import com.findJob.app.model.Account;
import com.findJob.app.model.Client;
import com.findJob.app.model.Role;
import com.findJob.app.model.dto.RegDto;
import com.findJob.app.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserServ {

    @Autowired
    private UserRepo userRepo;

    public void save(RegDto dto){

        Account account =new Account();

        account.setRole(Role.USER);

        account.setPassword("{noop}"+dto.getPassword());

        account.setPhone(dto.getPhone());

        account.setLinkedIn(dto.getLinkedIn());

        Client client = new Client();

        client.setAccount(account);

        client.setName(dto.getName());

        client.setSecondName(dto.getSecondName());

        client.setDescription(dto.getDescription());

        userRepo.save(client);
    }
}
