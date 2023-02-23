package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.models.Hospital;
import peaksoft.service.HospitalService;
/**
 * Zholdoshov Nuradil
 * peaksoft.api
 * 17.02.2023
 **/
@Controller
@RequestMapping("/hospital")
public class HospitalApi {

    private final HospitalService hospitalService;

    @Autowired
    public HospitalApi(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public String getAll(Model model,@RequestParam(value = "keyWord",required = false) String keyWord) {
        model.addAttribute("hospitals", hospitalService.getAllHospital(keyWord));
        model.addAttribute("keyWord",keyWord);
        return "hospital/mainPage";
    }

    @GetMapping("/new")
    public String newHospital(Model model) {
        model.addAttribute("hospital", new Hospital());
        return "hospital/new";
    }

    @PostMapping("/save")
    public String saveHospital(@ModelAttribute("hospital") @Valid Hospital hospital,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "hospital/new";
        }
        hospitalService.saveHospital(hospital);
        return "redirect:/hospital";
    }

    @GetMapping("/{id}/byId")
    public String byId(Model model, @PathVariable("id") Long id) {
        Hospital hospital = hospitalService.findById(id);
        model.addAttribute("hospital", hospital);
        model.addAttribute("doctors", hospital.getDoctorList().size());
        model.addAttribute("patients", hospital.getPatientList().size());
        return "hospital/show";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        hospitalService.delete(id);
        return "redirect:/hospital";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("hospital", hospitalService.findById(id));
        return "hospital/edit";
    }

    @PutMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute() @Valid Hospital hospital,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "hospital/edit";
        }
        hospitalService.update(id, hospital);
        return "redirect:/hospital/" + id + "/byId";
    }


}
