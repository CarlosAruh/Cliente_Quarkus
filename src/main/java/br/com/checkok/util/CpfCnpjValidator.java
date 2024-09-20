package br.com.checkok.util;

public class CpfCnpjValidator {

	public static boolean isDocumentoValido(String cpf, String cnpj) {
		boolean cpfValido = (cpf != null && !cpf.isEmpty()) ? validarCpf(cpf) : true;
		boolean cnpjValido = (cnpj != null && !cnpj.isEmpty()) ? validarCnpj(cnpj) : true;
		return (cpfValido || cnpjValido) && !(cpf == null && cnpj == null);
	}

	public static boolean validarCpf(String cpf) {
		if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d{11}")) {
			return false;
		}

		int d1, d2, sm, i, r;
		int[] cpfArray = new int[11];

		for (i = 0; i < 11; i++) {
			cpfArray[i] = Character.getNumericValue(cpf.charAt(i));
		}

		sm = 0;
		for (i = 0; i < 9; i++) {
			sm += (10 - i) * cpfArray[i];
		}
		r = 11 - (sm % 11);
		d1 = (r >= 10) ? 0 : r;

		if (d1 != cpfArray[9]) {
			return false;
		}

		sm = 0;
		for (i = 0; i < 10; i++) {
			sm += (11 - i) * cpfArray[i];
		}
		r = 11 - (sm % 11);
		d2 = (r >= 10) ? 0 : r;

		return d2 == cpfArray[10];
	}

	public static boolean validarCnpj(String cnpj) {
		if (cnpj == null || cnpj.length() != 14 || !cnpj.matches("\\d{14}")) {
			return false;
		}

		int d1, d2, sm, i, r;
		int[] cnpjArray = new int[14];

		for (i = 0; i < 14; i++) {
			cnpjArray[i] = Character.getNumericValue(cnpj.charAt(i));
		}

		sm = 0;
		int[] pesos1 = { 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
		for (i = 0; i < 12; i++) {
			sm += cnpjArray[i] * pesos1[i];
		}
		r = sm % 11;
		d1 = (r < 2) ? 0 : 11 - r;

		if (d1 != cnpjArray[12]) {
			return false;
		}

		sm = 0;
		int[] pesos2 = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
		for (i = 0; i < 13; i++) {
			sm += cnpjArray[i] * pesos2[i];
		}
		r = sm % 11;
		d2 = (r < 2) ? 0 : 11 - r;

		return d2 == cnpjArray[13];
	}

}