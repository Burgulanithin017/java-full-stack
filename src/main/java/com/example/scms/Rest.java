package com.example.scms;



import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class Rest {
    @GetMapping("/home")
    public  String home() {
   	 return "home";
    }
    
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login"; // This will return the HTML login page
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username, @RequestParam String password) {
        // Create a new User object and set the username and password
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.saveUser(user);
        return "redirect:/login"; // Redirect to the login page after registration
    }
    @GetMapping("/register")
    public String register() {
    	return "register";
    }
   
    @GetMapping("/studentdata")
    public  String studnetdata() {
     	 return "studnetdata";
   }
    @GetMapping("/counsellingdiary")
    public  String counsellingdiary() {
     	 return "counsellingdiary";
   }
    @GetMapping("/SCMS")
    public String SCMS() {
    	return "SCMS";
    }
    @PostMapping("/SCMS")
    public String scm() {
    	return "SCMS";
    }
    
   
   
    @GetMapping("/contact")
    public String contact() {
    	return "contact";
    }
    @PostMapping("/processContact")
    public String processContact() {
    	return "processContact";
    }
    
    @Autowired
    private StudentRepository studentRepository;
    @GetMapping("/addstudent")
    public String listStudents(Model model) {
        Iterable<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "addstudent"; // Create a Thymeleaf template (HTML) for this view
    }

    @PostMapping("/studentdata")
    public String addStudent(Student student) {
        studentRepository.save(student);
        return "redirect:/studentdata";
    }
    
    @Autowired
    private AppointmentService appointmentService;
    @GetMapping("/AppointmentScheduling")
    public String showAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        return "AppointmentScheduling";
    }
    @PostMapping("/processAppointment")
    public String processAppointmentForm(@ModelAttribute Appointment appointment) {
        appointmentService.saveAppointment(appointment);
        return "redirect:/processAppointment";
    }
    @GetMapping("/processAppointment")
    public String showAppointments(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        model.addAttribute("appointments", appointments);
        return "processAppointment";
    }
    @Autowired
    private DiaryEntryRepository entryRepository;

    @GetMapping("/diary")
    public String home(Model model) {
        Iterable<DiaryEntry> entries = entryRepository.findAll();
        model.addAttribute("entries", entries);
        model.addAttribute("newEntry", new DiaryEntry());
        return "diary";
    }

    @PostMapping("/addEntry")
    public String addEntry(@Validated DiaryEntry newEntry, Model model) {
        entryRepository.save(newEntry);
        // Add the newly added entry to the model so it can be displayed on the same page
        model.addAttribute("newEntry", newEntry);
        return "redirect:/diary"; // Redirect back to the home page after adding an entry
    }
        @InitBinder
        public void initBinder(WebDataBinder binder) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        }
    


}
