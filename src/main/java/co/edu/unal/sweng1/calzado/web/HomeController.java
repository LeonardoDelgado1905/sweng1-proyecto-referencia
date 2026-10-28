package co.edu.unal.sweng1.calzado.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("titulo", "SWENG1 — Importadora de calzado");
		return "index";
	}
}
