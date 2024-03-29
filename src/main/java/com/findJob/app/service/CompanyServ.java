package com.findJob.app.service;

import com.findJob.app.model.Account;
import com.findJob.app.model.Company;
import com.findJob.app.model.Role;
import com.findJob.app.model.dto.RegDto;
import com.findJob.app.repo.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServ {
    @Autowired
    private CompanyRepo companyRepo;

    public void save(RegDto dto){

        Account account =new Account();

        account.setEmail(dto.getEmail());

        account.setRole(Role.COMPANY);

        account.setPassword("{noop}"+dto.getPassword());

        account.setPhone(dto.getPhone());

        account.setLinkedIn(dto.getLinkedIn());

        Company company = new Company();

        company.setAccount(account);

        company.setName(dto.getName());

        company.setDescription(dto.getDescription());

        companyRepo.saveAndFlush(company);
    }
}
