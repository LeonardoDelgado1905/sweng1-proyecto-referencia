package co.edu.unal.sweng1.calzado.negocio;

/**
 * Resultado del cálculo de CIF, gravamen, IVA y total a pagar.
 */
public record ValoresDerivados(
		ValorDerivado cif,
		ValorDerivado gravamen,
		ValorDerivado iva,
		ValorDerivado totalAPagar) {
}
