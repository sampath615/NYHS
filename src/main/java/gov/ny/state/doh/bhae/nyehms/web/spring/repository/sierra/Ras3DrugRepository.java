package gov.ny.state.doh.bhae.nyehms.web.spring.repository.sierra;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.sierra.Ras3Drug;

/** The drugs of the Sierra (HIVdb) algorithm (table RAS3_DRUG). */
public interface Ras3DrugRepository extends JpaRepository<Ras3Drug, Integer> {
}
