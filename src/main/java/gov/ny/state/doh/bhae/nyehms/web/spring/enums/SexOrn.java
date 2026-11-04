package gov.ny.state.doh.bhae.nyehms.web.spring.enums;

public enum SexOrn {
	
	S("StrHetro"),
	B("Bisx"),
	U("Unk"),
	G("LesbGayHom");
		
	
	private String Name;
	
	SexOrn(String Name){
		this.Name = Name;
	}
	
	public String getName() {
	    return Name;
	}
	
	public static SexOrn fromString(String text) {
        for (SexOrn b : SexOrn.values()) {
            if (b.Name.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
	
}
