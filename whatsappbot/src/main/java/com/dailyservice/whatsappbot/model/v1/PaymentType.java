package com.dailyservice.whatsappbot.model.v1;

public enum PaymentType {
	COD("Cash on Delivery"),
	UPI("UPI"),
	CASH("CASH");
	
	public final String name;       

    private PaymentType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }
	
	
}
