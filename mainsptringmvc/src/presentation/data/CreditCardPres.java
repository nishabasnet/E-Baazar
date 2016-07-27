package presentation.data;

import business.externalinterfaces.CreditCard;

public class CreditCardPres {

	private String name;
	private String ccNumber;
	private String ccExpDate;
	private String ccType;
	

	public String getCcNumber() {
		return ccNumber;
	}

	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}

	public String getCcExpDate() {
		return ccExpDate;
	}

	public void setCcExpDate(String ccExpDate) {
		this.ccExpDate = ccExpDate;
	}

	public String getCcType() {
		return ccType;
	}

	public void setCcType(String ccType) {
		this.ccType = ccType;
	}

	public String getName() {
		return name;
	}

	public CreditCardPres(CreditCard cc){
		setName(cc.getNameOnCard());
		setCcNumber(cc.getCardNum());
		setCcExpDate(cc.getExpirationDate());
		setCcType(cc.getCardType());
	}
	
	public void setName(String name){
		this.name=name;
	}
}
