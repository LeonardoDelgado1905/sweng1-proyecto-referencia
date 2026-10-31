package co.edu.unal.sweng1.calzado.negocio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Calcula los valores derivados de una declaración a partir de sus datos base:
 * CIF = FOB + fletes + seguros; gravamen = CIF × gravamen (%) / 100;
 * IVA = (CIF + gravamen) × IVA (%) / 100; total a pagar = gravamen + IVA.
 * Los resultados se redondean a 2 decimales con {@link RoundingMode#HALF_UP}.
 */
public class CalculadoraValoresDerivados {

	public static final String FOB = "Valor FOB";
	public static final String FLETE = "Flete";
	public static final String SEGURO = "Seguro";
	public static final String GRAVAMEN_PORCENTAJE = "Gravamen (%)";
	public static final String IVA_PORCENTAJE = "IVA (%)";

	private static final BigDecimal CIEN = BigDecimal.valueOf(100);

	/**
	 * Calcula los valores derivados. Cualquier argumento puede ser nulo, vacío
	 * o no numérico; en ese caso los valores que dependen de él no se calculan
	 * y se informa el dato faltante.
	 */
	public ValoresDerivados calcular(String fob, String flete, String seguro,
			String gravamenPorcentaje, String ivaPorcentaje) {
		Dato datoFob = new Dato(FOB, fob);
		Dato datoFlete = new Dato(FLETE, flete);
		Dato datoSeguro = new Dato(SEGURO, seguro);
		Dato datoGravamen = new Dato(GRAVAMEN_PORCENTAJE, gravamenPorcentaje);
		Dato datoIva = new Dato(IVA_PORCENTAJE, ivaPorcentaje);

		List<String> faltantesCif = faltantes(datoFob, datoFlete, datoSeguro);
		List<String> faltantesGravamen = faltantes(datoFob, datoFlete, datoSeguro, datoGravamen);
		List<String> faltantesIva = faltantes(datoFob, datoFlete, datoSeguro, datoGravamen, datoIva);

		if (!faltantesCif.isEmpty()) {
			return new ValoresDerivados(
					sinCalcular(faltantesCif),
					sinCalcular(faltantesGravamen),
					sinCalcular(faltantesIva),
					sinCalcular(faltantesIva));
		}

		BigDecimal cif = datoFob.numero.add(datoFlete.numero).add(datoSeguro.numero);
		ValorDerivado valorCif = new ValorDerivado(redondear(cif), List.of());
		if (!faltantesGravamen.isEmpty()) {
			return new ValoresDerivados(valorCif, sinCalcular(faltantesGravamen),
					sinCalcular(faltantesIva), sinCalcular(faltantesIva));
		}

		BigDecimal gravamen = cif.multiply(datoGravamen.numero).divide(CIEN);
		ValorDerivado valorGravamen = new ValorDerivado(redondear(gravamen), List.of());
		if (!faltantesIva.isEmpty()) {
			return new ValoresDerivados(valorCif, valorGravamen,
					sinCalcular(faltantesIva), sinCalcular(faltantesIva));
		}

		BigDecimal iva = cif.add(gravamen).multiply(datoIva.numero).divide(CIEN);
		BigDecimal total = redondear(gravamen).add(redondear(iva));
		return new ValoresDerivados(valorCif, valorGravamen,
				new ValorDerivado(redondear(iva), List.of()),
				new ValorDerivado(total, List.of()));
	}

	private static BigDecimal redondear(BigDecimal valor) {
		return valor.setScale(2, RoundingMode.HALF_UP);
	}

	private static ValorDerivado sinCalcular(List<String> datosFaltantes) {
		return new ValorDerivado(null, datosFaltantes);
	}

	private static List<String> faltantes(Dato... datos) {
		List<String> faltantes = new ArrayList<>();
		for (Dato dato : datos) {
			if (dato.numero == null) {
				faltantes.add(dato.nombre);
			}
		}
		return faltantes;
	}

	private static final class Dato {
		private final String nombre;
		private final BigDecimal numero;

		private Dato(String nombre, String texto) {
			this.nombre = nombre;
			this.numero = interpretar(texto);
		}

		private static BigDecimal interpretar(String texto) {
			if (texto == null || texto.isBlank()) {
				return null;
			}
			try {
				BigDecimal numero = new BigDecimal(texto.trim());
				return numero.signum() < 0 ? null : numero;
			} catch (NumberFormatException exception) {
				return null;
			}
		}
	}
}
