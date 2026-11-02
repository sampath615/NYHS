package gov.ny.state.doh.bhae.nyehms.ehars.record;

/**
 * An eHARS facility's address and the description of its type.
 */
public record EharsFacilityDetails(String address, String city, String countyFips, String state, String zipCode, String phone,
		String country, String typeDescription) {
}
