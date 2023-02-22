package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.models.Department;
import peaksoft.service.DepartmentService;
import peaksoft.service.HospitalService;

/**
 * Zholdoshov Nuradil
 * peaksoft.api
 * 17.02.2023
 **/
@Controller
@RequestMapping("/department")
public class DepartmentApi {

    private final DepartmentService departmentService;
    private final HospitalService hospitalService;

    @Autowired
    public DepartmentApi(DepartmentService departmentService, HospitalService hospitalService) {
        this.departmentService = departmentService;
        this.hospitalService = hospitalService;
    }

    @GetMapping("/{id}")
    public String getAll(Model model, @PathVariable("id") Long id) {
        model.addAttribute("departments", departmentService.getAll(id));
        model.addAttribute("hospitalId", id);
        model.addAttribute("hospital", hospitalService.findById(id));
        return "department/mainPage";
    }

    @GetMapping("/{hospitalId}/new")
    public String newDepartment(Model model, @PathVariable("hospitalId") Long id) {
        model.addAttribute("department", new Department());
        model.addAttribute("hospitalId", id);
        return "department/new";
    }

    @PostMapping("/{hospitalId}/save")
    public String save(@PathVariable Long hospitalId, @ModelAttribute("department") @Valid Department department
            , BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "department/new";
            }
            departmentService.save(department, hospitalId);
            return "redirect:/department/" + hospitalId;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @GetMapping("/{hospitalId}/{id}/edit")
    public String edit(@PathVariable Long hospitalId, @PathVariable Long id, Model model) {
        model.addAttribute("department", departmentService.findById(id));
        model.addAttribute(hospitalId);
        return "department/edit";
    }

    @PutMapping("/{hospitalId}/{id}/update")
    public String update(@PathVariable Long hospitalId, @PathVariable Long id, @ModelAttribute("newDepartment") @Valid Department department
            , BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("department", department);
            return "department/edit";
        }
        departmentService.update(id, department);
        return "redirect:/department/" + hospitalId;
    }

    @DeleteMapping("/{hospitalId}/{id}/delete")
    public String delete(@PathVariable Long hospitalId, @PathVariable Long id) {
        departmentService.delete(id);
        return "redirect:/department/" + hospitalId;
    }

    @GetMapping("/{hospitalId}/{departmentId}/doctors")
    public String getAllDoctorsByDepartmentId(@PathVariable Long departmentId, @PathVariable Long hospitalId, Model model) {
        model.addAttribute("department", departmentService.findById(departmentId).getName());
        model.addAttribute("doctors", departmentService.getAllDoByDepId(departmentId));
        model.addAttribute(hospitalId);
        return "department/doctors";
    }

}

