package com.dailyservice.whatsappbot.model.v1;

public enum UnitType {

	GR("Gram"),
	KG("Kilo Gram"),
	L("Liter");
	
	private final String name;       

    private UnitType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }
    
    public String getName() {
    	return this.name;
    }
	
}
