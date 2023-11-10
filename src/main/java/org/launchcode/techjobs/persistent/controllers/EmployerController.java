package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("employers/") // Had to add "/" so the redirect in processAddEmployerForm would work correctly. However, with most web pages, it doesn't matter whether you append a "/" to the end of the URL or not. I want to figure out how to make that work here.

public class EmployerController {

    @Autowired
    private EmployerRepository employerRepository;
// HTTP get requests /
    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("title", "All Employers");
        model.addAttribute("employers", employerRepository.findAll());

        return "employers/index";
    }
 // HTTP get request add
    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {

        model.addAttribute(new Employer());

        return "employers/add";
    }

    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Employer newEmployer, Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "employers/add";
        }

        employerRepository.save(newEmployer); // using the variable to send the input to the database via the data layer

        return "redirect:";
    }
// get requests for view/employerid
    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable int employerId) {

        Optional optEmployer = employerRepository.findById(employerId);
        if (optEmployer.isPresent()) {
            Employer employer = (Employer) optEmployer.get();
            model.addAttribute("employer", employer);
            return "employers/view";
        } else {
            return "redirect:../";
        }

    }

}