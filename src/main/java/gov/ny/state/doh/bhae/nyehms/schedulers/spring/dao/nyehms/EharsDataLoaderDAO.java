package gov.ny.state.doh.bhae.nyehms.schedulers.spring.dao.nyehms;

import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.jpa.JpqlStatements;
import lombok.RequiredArgsConstructor;

/**
 * The eHARS data loader's table maintenance (formerly a MyBatis mapper with the same name and method; the caller is
 * unchanged): a bulk JPQL delete over EharsRidr.
 */
@Repository
@RequiredArgsConstructor
public class EharsDataLoaderDAO {

	private final JpqlStatements jpql;

	/** Empties EHARS_RIDR before a reload. */
	public void deleteRIDREharsData() {
		jpql.update("delete from EharsRidr r");
	}
}
