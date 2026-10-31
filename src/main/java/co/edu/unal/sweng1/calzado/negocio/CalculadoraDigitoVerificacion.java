package co.edu.unal.sweng1.calzado.negocio;

/**
 * Calcula y valida el dígito de verificación de un NIT mediante el método
 * módulo 11 de la DIAN.
 */
public class CalculadoraDigitoVerificacion {

	private static final int[] PESOS = {
			3, 7, 13, 17, 19, 23, 29, 37, 41, 43, 47, 53, 59, 67, 71
	};

	/**
	 * Calcula el dígito de verificación correspondiente al NIT.
	 *
	 * @param nit NIT compuesto únicamente por entre 1 y 15 dígitos
	 * @return dígito de verificación calculado
	 * @throws IllegalArgumentException si el NIT es nulo, vacío, contiene
	 *         caracteres distintos de dígitos o tiene más de 15 dígitos
	 */
	public int calcular(String nit) {
		if (nit == null || nit.isEmpty() || nit.length() > PESOS.length) {
			throw new IllegalArgumentException("El NIT debe contener entre 1 y 15 dígitos.");
		}

		int suma = 0;
		for (int indice = 0; indice < nit.length(); indice++) {
			char digito = nit.charAt(nit.length() - 1 - indice);
			if (digito < '0' || digito > '9') {
				throw new IllegalArgumentException("El NIT solo puede contener dígitos.");
			}
			suma += (digito - '0') * PESOS[indice];
		}

		int residuo = suma % 11;
		return residuo <= 1 ? residuo : 11 - residuo;
	}

	/**
	 * Comprueba si el dígito de verificación corresponde al NIT.
	 *
	 * @param nit NIT compuesto únicamente por entre 1 y 15 dígitos
	 * @param digitoVerificacion dígito de verificación que se desea comprobar
	 * @return {@code true} si el dígito corresponde al NIT; {@code false} en
	 *         caso contrario
	 * @throws IllegalArgumentException si el NIT es nulo, vacío, contiene
	 *         caracteres distintos de dígitos o tiene más de 15 dígitos
	 */
	public boolean esValido(String nit, int digitoVerificacion) {
		return calcular(nit) == digitoVerificacion;
	}
}
