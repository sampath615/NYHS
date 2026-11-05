package gov.ny.state.doh.bhae.nyehms.web.spring.record;

/**
 * A worker's region and role as the profile screen lists it: the dates as MM/DD/YYYY text.
 */
public record RegionRoleRow(Long workerRegionId, String roleDesc, String regionDescription, String startDate, String endDate) {
}
