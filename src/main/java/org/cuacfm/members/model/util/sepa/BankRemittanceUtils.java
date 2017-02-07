
package org.cuacfm.members.model.util.sepa;

import java.math.BigInteger;

/**
 * The Class BankRemittanceUtils.
 */
public class BankRemittanceUtils {

	/**
	 * Instantiates a new bank remittance utils.
	 */
	private BankRemittanceUtils() {
		super();
	}

	/**
	 * Validate IBAN.
	 *
	 * @param iban the iban
	 * @return true, if successful
	 */
	public static boolean validateIBAN(String iban) {
		String baseString = iban.substring(4) + iban.substring(0, 2) + "00";
		StringBuilder computeString = new StringBuilder();
		for (int i = 0; i < baseString.length(); i++) {
			char c = baseString.charAt(i);
			if (Character.isDigit(c)) {
				computeString.append(c);
			}
			if (Character.isLetter(c)) {
				computeString.append(Character.getNumericValue(c));
			}
		}
		BigInteger computeInt = new BigInteger(computeString.toString());
		BigInteger mod97 = computeInt.mod(new BigInteger("97"));
		BigInteger controldigit = (new BigInteger("98")).subtract(mod97);
		String strControlDigit = controldigit.toString();
		if (strControlDigit.length() < 2) {
			strControlDigit = "0" + strControlDigit;
		}
		return strControlDigit.equals(iban.substring(2, 4));
	}

	/**
	 * Calculate SEPA creditor ID.
	 *
	 * @param isoCountry the iso country
	 * @param sufijo the sufijo
	 * @param nif the nif
	 * @return the string
	 */
	public static String calculateSEPACreditorID(String isoCountry, String sufijo, String nif) {
		String baseString = nif + isoCountry + "00";
		StringBuilder computeString = new StringBuilder();
		for (int i = 0; i < baseString.length(); i++) {
			char c = baseString.charAt(i);
			if (Character.isDigit(c)) {
				computeString.append(c);
			}
			if (Character.isLetter(c)) {
				computeString.append(Character.getNumericValue(c));
			}
		}
		BigInteger computeInt = new BigInteger(computeString.toString());
		BigInteger mod97 = computeInt.mod(new BigInteger("97"));
		BigInteger controldigit = (new BigInteger("98")).subtract(mod97);
		String strControlDigit = controldigit.toString();
		if (strControlDigit.length() < 2) {
			strControlDigit = "0" + strControlDigit;
		}
		return isoCountry + strControlDigit + sufijo + nif;
	}

	/**
	 * Calculate IBAN.
	 *
	 * @param isoCountry the iso country
	 * @param ncc the ncc
	 * @return the string
	 */
	public static String calculateIBAN(String isoCountry, String ncc) {
		String baseString = ncc + isoCountry + "00";
		StringBuilder computeString = new StringBuilder();
		for (int i = 0; i < baseString.length(); i++) {
			char c = baseString.charAt(i);
			if (Character.isDigit(c)) {
				computeString.append(c);
			}
			if (Character.isLetter(c)) {
				computeString.append(Character.getNumericValue(c));
			}
		}
		BigInteger computeInt = new BigInteger(computeString.toString());
		BigInteger mod97 = computeInt.mod(new BigInteger("97"));
		BigInteger controldigit = (new BigInteger("98")).subtract(mod97);
		String strControlDigit = controldigit.toString();
		if (strControlDigit.length() < 2) {
			strControlDigit = "0" + strControlDigit;
		}
		return isoCountry + strControlDigit + ncc;
	}

}
