package gov.ny.state.doh.bhae.nyehms.web.spring.service.document;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;

import gov.ny.state.doh.bhae.nyehms.ehars.record.EharsCode;
import gov.ny.state.doh.bhae.nyehms.ehars.record.EharsFacilityDetails;
import gov.ny.state.doh.bhae.nyehms.ehars.repository.EharsCountryCodeRepository;
import gov.ny.state.doh.bhae.nyehms.ehars.repository.EharsFacilityCodeRepository;
import gov.ny.state.doh.bhae.nyehms.ehars.repository.EharsLookupCodeRepository;
import lombok.RequiredArgsConstructor;

/**
 * Code lists and facilities read from the eHARS database. If eHARS cannot be read, the error is logged and the data is
 * empty: the screens that show this data still open.
 */
@Service
@RequiredArgsConstructor
public class EharsDataService {

	private static final Logger log = LoggerFactory.getLogger(EharsDataService.class);

	private final EharsCountryCodeRepository countryCodeRepository;
	private final EharsLookupCodeRepository lookupCodeRepository;
	private final EharsFacilityCodeRepository facilityCodeRepository;

	/** The country codes and descriptions, by description. */
	public List<EharsCode> getCountryCodes() {
		return readOrEmpty(countryCodeRepository::findCodesByDescription);
	}

	/** The codes and names of a coded field, by name. */
	public List<EharsCode> getCodesOfField(String fieldName) {
		return readOrEmpty(() -> lookupCodeRepository.findCodesOfType(fieldName));
	}

	/** The ids and names of the given facilities, by name; none if no ids are given. */
	public List<EharsCode> getFacilityNames(String[] facilityUids) {
		if (facilityUids == null || facilityUids.length == 0) {
			return List.of();
		}
		return readOrEmpty(() -> facilityCodeRepository.findNamesOfFacilities(Arrays.asList(facilityUids)));
	}

	/** A facility's address and type; the last row if the query finds several, as the screen always took. */
	public Optional<EharsFacilityDetails> getFacilityDetails(String facilityUid) {
		List<EharsFacilityDetails> details = readOrEmpty(() -> facilityCodeRepository.findDetailsOfFacility(facilityUid));
		return details.isEmpty() ? Optional.empty() : Optional.of(details.get(details.size() - 1));
	}

	private static <T> List<T> readOrEmpty(Supplier<List<T>> read) {
		try {
			return read.get();
		} catch (DataAccessException | TransactionException e) {
			log.error("Exception while retrieving the Ehars data {}", e.getMessage());
			return List.of();
		}
	}
}
