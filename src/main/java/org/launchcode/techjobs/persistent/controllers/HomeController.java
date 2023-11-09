package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private JobRepository jobRepository;

    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("title", "MyJobs");
        List jobs = (List<Job>) jobRepository.findAll();
        model.addAttribute("jobs",jobs);

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());
        List employers = (List<Employer>) employerRepository.findAll();
        List skills = (List<Skill>) skillRepository.findAll();
        model.addAttribute("employers",employers);
        model.addAttribute("skills", skills);

        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                    Errors errors, Model model, @RequestParam int employerId,
                                    @RequestParam @Valid List<Integer> skills) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            return "add";
        }
        Optional<Employer> optionalEmployer = employerRepository.findById(employerId);
        if(optionalEmployer.isPresent()){
            Employer employer = optionalEmployer.get();
            newJob.setEmployer(employer);
        }

        model.addAttribute("employers",employerRepository.findById(employerId));

        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
        newJob.setSkills(skillObjs);


        jobRepository.save(newJob);


        return "redirect:./";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
        Optional<Job> optjob = jobRepository.findById(jobId);
        if (optjob.isPresent()) {
            Job job = optjob.get();
            model.addAttribute("jobs", job);
            return "view";
        } else {
            return "redirect:./";
        }


    }

}