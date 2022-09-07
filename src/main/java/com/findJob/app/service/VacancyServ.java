package com.findJob.app.service;

import com.findJob.app.model.*;
import com.findJob.app.model.dto.VacDto;
import com.findJob.app.repo.ClientRepo;
import com.findJob.app.repo.VacancyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class VacancyServ {
    @Autowired
    private VacancyRepo vacancyRepo;

    @Autowired
    private ClientRepo clientRepo;

    public List<Vacancy> getRandom(){
        return vacancyRepo.findAll().subList(0,3);
    }

    public List<Vacancy> getAll(){
        return vacancyRepo.findAll();
    }

    public List<Vacancy> getFilter(Integer salary, List<Level> levels, Category category){
        if (salary==null)salary = 0;
        if (levels ==null){
            levels = new ArrayList<Level>(Arrays.asList(Level.values()));

        }

        List<Vacancy>list =vacancyRepo.getByFilter(salary,levels);

        if (category!=null)
            list = list.stream().filter(vacancy -> vacancy.getCategories().contains(category)).collect(Collectors.toList());

        return list;
    }
    public Vacancy getCategoryById(Integer id){
        return vacancyRepo.getById(id);
    }

    public void save(VacDto vacDto){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currentPrincipalName = (Account)authentication.getPrincipal();

        Vacancy vacancy = new Vacancy();
        vacancy.setName(vacDto.getName());
        vacancy.setBigDescription(vacDto.getBig());
        vacancy.setSalary(vacDto.getSalary());
        vacancy.setSmallDescription(vacDto.getSmall());
        vacancy.setLevel(vacDto.getLevel());
        Company company = new Company();
        company.setId(currentPrincipalName.getId());
        vacancy.setCategories(vacDto.getCategories());
        vacancy.setCompany(company);

        vacancyRepo.save(vacancy);
    }

    @Transactional
    public void startConversation(Integer vacancyId){
        Vacancy vacancy = vacancyRepo.getById(vacancyId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currentPrincipalName = (Account)authentication.getPrincipal();

        Client client = clientRepo.getById(currentPrincipalName.getId());

        vacancy.getClients().add(client);
        vacancyRepo.saveAndFlush(vacancy);
    }
    public List<Vacancy> getCompanyVacancies(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currentPrincipalName = (Account)authentication.getPrincipal();
        Company company = new Company();
        company.setId(currentPrincipalName.getId());
        return vacancyRepo.getByCompany(company);
    }
}
