package org.cuacfm.members.model.exceptions;


/** The Class ExistTransactionIdException.*/
@SuppressWarnings("serial")
public class ExistTransactionIdException extends Exception {

	/** The id txn. */
	private String idTxn;

	/**
	 * Instantiates a new exist transaction id exception.
	 *
	 * @param idTxn
	 *            the id txn
	 */
	public ExistTransactionIdException(String idTxn) {
		super("It already Exist Transaction Id: " + idTxn);
		this.idTxn = idTxn;
	}

	/**
	 * Gets the id txn.
	 *
	 * @return the id txn
	 */
	public String getIdTxn() {
		return idTxn;
	}

}
