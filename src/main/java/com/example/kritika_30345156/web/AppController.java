package com.example.kritika_30345156.web;

import com.example.kritika_30345156.entities.Salesman;
import com.example.kritika_30345156.repositories.SalesRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@SessionAttributes("e")
@AllArgsConstructor
@Controller
public class AppController {

    @Autowired
    private SalesRepository salesRepository;

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("sales", new Salesman());
        List<Salesman> listTransactions = salesRepository.findAll();
        model.addAttribute("listTransactions", listTransactions);
        return "index";
    }

    @PostMapping(path = "/save")
    public String save(Model model, Salesman sales, BindingResult bindingResult, ModelMap mm) {
        if (bindingResult.hasErrors()) {
            return "index";
        } else {
            String name= sales.getName();
            Date date = sales.getDot();
            if(sales.getAmount()==0){
                mm.put("e", 1);
                return "redirect:index";
            }else if(name.equals("")){
                mm.put ("e", 2);
                return "redirect:index";
            }else if(date.equals("")){
                mm.put ("e", 3);
                return "redirect:index";
            }
            mm.put("e",0);
            salesRepository.save(sales);
            return "redirect:index";
        }
    }

    @GetMapping("/delete")
    public String delete(Long id) {
        salesRepository.deleteById(id);
        return "redirect:/index";
    }

    @GetMapping("/edit")
    public String editTransaction(Model model, Long id){
        Salesman salesman = salesRepository.findById(id).orElse(null);
        if(salesman==null) throw new RuntimeException("Sales does not exist");
        model.addAttribute("salesman", salesman);
        return "editTransaction";
    }


}
