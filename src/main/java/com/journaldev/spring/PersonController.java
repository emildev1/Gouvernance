package com.journaldev.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.journaldev.spring.model.Person;
import com.journaldev.spring.model.Project;
import com.journaldev.spring.service.PersonService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonController {

    private PersonService personService;

    @Autowired(required = true)
    @Qualifier(value = "personService")
    public void setPersonService(PersonService ps) {
        this.personService = ps;
    }

    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public String listPersons(Model model) {
        model.addAttribute("person", new Person());
        model.addAttribute("project", new Project());
        model.addAttribute("listPersons", this.personService.listPersons());
        return "person";
    }

    @RequestMapping(value = "/redirectToPage", method = RequestMethod.GET)
    public String redirectToPage(@RequestParam String pageName) {
        //model.addAttribute("person", new Person());
        //model.addAttribute("listPersons", this.personService.listPersons());
        return pageName;
    }

    //For add and update person both
    @RequestMapping(value = "/person/add", method = RequestMethod.POST)
    public String addPerson(@ModelAttribute("person") Person p) {

        if (p.getId() == 0) {
            //new person, add it
            this.personService.addPerson(p);
        } else {
            //existing person, call update
            this.personService.updatePerson(p);
        }

        return "redirect:/persons";

    }

    @RequestMapping(value = "/project/init", method = RequestMethod.POST)
    public String addProject(@ModelAttribute("project") Project p) {

        /*if(p.getId() == 0){
			//new person, add it
			this.personService.addPerson(p);
		}else{
			//existing person, call update
			this.personService.updatePerson(p);
		}*/
        System.out.println("----------------------------");
        System.out.println(p.getToken());
        System.out.println("----------------------------");
        return "redirect:/persons";

    }

    @RequestMapping("/remove/{id}")
    public String removePerson(@PathVariable("id") int id) {

        this.personService.removePerson(id);
        return "redirect:/persons";
    }

    @RequestMapping("/edit/{id}")
    public String editPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", this.personService.getPersonById(id));
        model.addAttribute("listPersons", this.personService.listPersons());
        return "person";
    }

}
