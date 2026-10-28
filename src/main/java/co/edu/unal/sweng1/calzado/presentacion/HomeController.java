package co.edu.unal.sweng1.calzado.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import co.edu.unal.sweng1.calzado.negocio.DeclaracionService;

@Controller
public class HomeController {

	private final DeclaracionService declaracionService;

	public HomeController(DeclaracionService declaracionService) {
		this.declaracionService = declaracionService;
	}

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("titulo", "SWENG1 — Importadora de calzado");
		model.addAttribute("declaraciones", declaracionService.listar());
		return "index";
	}
}
