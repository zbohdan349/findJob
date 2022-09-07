package com.findJob.app.controller;

import com.findJob.app.model.*;
import com.findJob.app.model.dto.FilterReq;
import com.findJob.app.model.dto.RegDto;
import com.findJob.app.model.dto.VacDto;
import com.findJob.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class StartController {

    @Autowired
    private CompanyServ companyServ;

    @Autowired
    private UserServ userServ;

    @Autowired
    private VacancyServ vacancyServ;

    @Autowired
    private CategoryServ categoryServ;

    @GetMapping("/")
    public  String start(Model model){

        model.addAttribute("vacancies",vacancyServ.getRandom());
        return "index";
    }
    @GetMapping("/find")
    public  String find(Model model){
        model.addAttribute("filterReq", new FilterReq());
        model.addAttribute("categories",categoryServ.getAllCategories());
        model.addAttribute("vacancies",vacancyServ.getAll());
        model.addAttribute("levels", Level.values());
        return "filterP";
    }
    @GetMapping("/find1")
    public  String findWithParam(Model model,
                                 @DefaultValue("0")@RequestParam(required = false) Integer salary,
                                 @RequestParam(required = false) List<Level> levels,
                                 @RequestParam(required = false) Category category
                                 ){
        model.addAttribute("filterReq", new FilterReq());

        model.addAttribute("categories",categoryServ.getAllCategories());

        model.addAttribute("vacancies",vacancyServ.getFilter(salary,levels,category));

        model.addAttribute("levels", Level.values());

        return "filterP";
    }
    @GetMapping("/vacancy/{id}")
    public  String vacancy(Model model,@PathVariable Integer id){
        model.addAttribute("vacancy",vacancyServ.getCategoryById(id));
        return "vacancy";
    }
    @GetMapping("/vacancy/update/{id}")
    public  String startConversation(Model model,@PathVariable Integer id){
        vacancyServ.startConversation(id);
        return "redirect:/find";
    }
    @GetMapping("/addVacancy")
    public  String addVacancyGet(Model model){
        model.addAttribute("levels", Level.values());
        model.addAttribute("vacDto",new VacDto());
        model.addAttribute("categories",categoryServ.getAllCategories());
        return "addVacancy";
    }
    @GetMapping("/getVacancies")
    public  String getVacancies(Model model){

        model.addAttribute("vacancies",vacancyServ.getCompanyVacancies());
        return "MyVacancion";
    }
    @PostMapping("/addVacancy")
    public  String addVacancy(@ModelAttribute VacDto vacDto, Model model){


        vacancyServ.save(vacDto);
        return "redirect:/find";
    }
    @GetMapping("/registration")
    public  String registration(Model model){

        return "RegForm1";
    }
    @GetMapping("/registration/client")
    public  String registrationUser(Model model){

        model.addAttribute("registration",new RegDto());

        return "RegForm2";
    }
    @PostMapping("/registration/client")
    public  String registrationUserPost(@ModelAttribute RegDto dto,Model model){

       model.addAttribute("registration",dto);

       userServ.save(dto);

       return "redirect:/login";
    }

    @GetMapping("/registration/company")
    public  String registrationCompany(Model model){

        model.addAttribute("registration",new RegDto());

        return "RegForm3";
    }

    @PostMapping("/registration/company")
    public  String registrationCompanyPost(@ModelAttribute RegDto dto,Model model){

        model.addAttribute("registration",dto);

        companyServ.save(dto);

        return "redirect:/login";
    }
}
