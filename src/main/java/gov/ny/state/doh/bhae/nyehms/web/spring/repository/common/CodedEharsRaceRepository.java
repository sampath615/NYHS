package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.CodedEharsRace;

/** The eHARS race codes (CODED_EHARS_RACE). */
public interface CodedEharsRaceRepository extends JpaRepository<CodedEharsRace, String> {

	/** A race code. */
	record Race(String eharsRaceCode, String eharsRaceDesc) {
	}

	/** All race codes, by code. */
	List<Race> findAllByOrderByEharsRaceCode();
}
