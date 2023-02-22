package peaksoft.api;

import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        model.addAttribute("hospitID", hospitalId);
        return "patient/mainPage";
    }

    @GetMapping("/{hospitalId}/new")
    public String newPatient(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute(hospitalId);
        return "patient/new";
    }

    @PostMapping("/{hospitalId}/save")
    public String save(@PathVariable Long hospitalId, @ModelAttribute("patient") @Valid Patient patient,
                       BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "patient/new";
        }
        try {
            patientService.save(patient, hospitalId);
            return "redirect:/patient/" + hospitalId;
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("Email", "This email already exists in the database");
            return "patient/new";
        } catch (RuntimeException e) {
            model.addAttribute("errorPhoneNumber", "Phone number already exist!");
            model.addAttribute(hospitalId);
            return "patient/new";
        }
    }

    @GetMapping("/{hospitalId}/{id}/edit")
    public String edit(@PathVariable Long id, @PathVariable Long hospitalId, Model model) {
        model.addAttribute("patient", patientService.findById(id));
        model.addAttribute( hospitalId);
        return "patient/edit";
    }

    @PutMapping("/{hospitalId}/{id}/update")
    public String update(@PathVariable Long hospitalId,@PathVariable Long id, @ModelAttribute("patient") @Valid Patient patient,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("patient", patient);
            model.addAttribute( hospitalId);
            return "patient/edit";
        }
        try {
            patientService.update(id, patient);
            return "redirect:/patient/" + patient.getHospitalId();
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("patient", patientService.findById(id));
            model.addAttribute( hospitalId);
            model.addAttribute("Email", "This email already exists in the database");
            return "patient/edit";
        } catch (RuntimeException e) {
            model.addAttribute("patient", patientService.findById(id));
            model.addAttribute( hospitalId);
            model.addAttribute("errorPhoneNumber", "Phone number already exist!");
            return "patient/edit";
        }
    }

    @DeleteMapping("/{hospitalId}/{id}/delete")
    public String delete(@PathVariable Long hospitalId, @PathVariable Long id) {
        patientService.delete(id);
        return "redirect:/patient/" + hospitalId;
    }
}
