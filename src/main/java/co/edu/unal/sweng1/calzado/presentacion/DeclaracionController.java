package co.edu.unal.sweng1.calzado.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.unal.sweng1.calzado.negocio.Declaracion;
import co.edu.unal.sweng1.calzado.negocio.DeclaracionNoValidableException;
import co.edu.unal.sweng1.calzado.negocio.CorreccionVaciaException;
import co.edu.unal.sweng1.calzado.negocio.DeclaracionService;

@Controller
public class DeclaracionController {

	private final DeclaracionService declaracionService;

	public DeclaracionController(DeclaracionService declaracionService) {
		this.declaracionService = declaracionService;
	}

	@GetMapping("/declaraciones/{id}")
	public String detalle(@PathVariable Long id, Model model) {
		cargarDeclaracion(id, model);
		return "declaracion";
	}

	@PostMapping("/declaraciones/{declaracionId}/campos/{campoId}")
	public String corregirCampo(
			@PathVariable Long declaracionId,
			@PathVariable Long campoId,
			@RequestParam String valor,
			Model model) {

		try{
			declaracionService.corregirCampo(declaracionId, campoId, valor);
		} catch (CorreccionVaciaException exception) {
			model.addAttribute(	"error", "El valor no puede estar vacío");
			cargarDeclaracion(declaracionId, model);
			return "declaracion";
		}

		return "redirect:/declaraciones/" + declaracionId;
	}

	@PostMapping("/declaraciones/{id}/validar")
	public String validar(@PathVariable Long id, Model model) {
		try {
			declaracionService.validar(id);
			return "redirect:/declaraciones/" + id;
		} catch (DeclaracionNoValidableException exception) {
			cargarDeclaracion(id, model);
			model.addAttribute("camposPendientes", exception.getCamposPendientes());
			return "declaracion";
		}
	}

	private void cargarDeclaracion(Long id, Model model) {
		Declaracion declaracion = declaracionService.buscar(id);
		model.addAttribute("declaracion", declaracion);
		model.addAttribute("camposPendientes", null);
	}
}
