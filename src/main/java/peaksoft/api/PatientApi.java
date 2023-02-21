package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.models.Patient;
import peaksoft.service.HospitalService;
import peaksoft.service.PatientService;

/**
 * Zholdoshov Nuradil
 * peaksoft.api
 * 18.02.2023
 **/
@Controller
@RequestMapping("/patient")
public class PatientApi {

    private final PatientService patientService;
    private final HospitalService hospitalService;

    @Autowired
    public PatientApi(PatientService patientService, HospitalService hospitalService) {
        this.patientService = patientService;
        this.hospitalService = hospitalService;
    }

    @GetMapping("/{hospitalId}")
    public String getAll(Model model, @PathVariable("hospitalId") Long hospitalId) {
        model.addAttribute("patients", patientService.getAll(hospitalId));
        model.addAttribute("hospital", hospitalService.findById(hospitalId));
        return "patient/mainPage";
    }

    @GetMapping("/new")
    public String newPatient(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("hospitals", hospitalService.getAllHospital());
        return "patient/new";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("patient") @Valid Patient patient,
                       BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("hospitals", hospitalService.getAllHospital());
            return "patient/new";
        }
        Long hospitalId = patient.getHospitalId();
        patientService.save(patient);
        return "redirect:/patient/" + hospitalId;
    }

    @GetMapping("/{hospitalId}/{id}/edit")
    public String edit(@PathVariable Long id, @PathVariable Long hospitalId, Model model) {
        model.addAttribute("patient", patientService.findById(id));
        model.addAttribute("hospitals", hospitalService.getAllHospital());
        model.addAttribute("hospitalId", hospitalId);
        return "patient/edit";
    }

    @PutMapping("/{id}/update")
    public String update(@PathVariable Long id, @ModelAttribute("patient") @Valid Patient patient,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("hospitals", hospitalService.getAllHospital());
            model.addAttribute("patient", patient);
            return "patient/edit";
        }
        patientService.update(id, patient);
        return "redirect:/patient/" + patient.getHospitalId();
    }

    @DeleteMapping("/{hospitalId}/{id}/delete")
    public String delete(@PathVariable Long hospitalId, @PathVariable Long id) {
        patientService.delete(id);
        return "redirect:/patient/" + hospitalId;
    }
}
