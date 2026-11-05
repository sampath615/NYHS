package gov.ny.state.doh.bhae.nyehms.web.spring.repository.report;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.report.PortalProviders;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.PortalProviderNameRow;

/** The provider portal's providers (table PORTAL_PROVIDERS). */
public interface PortalProvidersRepository extends JpaRepository<PortalProviders, BigDecimal> {

	/**
	 * The distinct providers (id and name) who uploaded a file of one of two file types themselves (PROVIDER_HCSID) or
	 * through a mapped user (PORTAL_PROVIDER_USER_MAPPING). The name parts are joined with {@code ||}, which keeps a
	 * missing part as an empty text in Oracle. (The former {@code in (... union ...)} is the equivalent {@code in ... or
	 * in ...}.)
	 */
	@Query("""
			select distinct new gov.ny.state.doh.bhae.nyehms.web.spring.record.PortalProviderNameRow(p.portalProvId,
			       p.providerFname || ' ' || p.providerMname || ' ' || p.providerLname)
			from PortalProviders p
			where p.portalProvId in (select p2.portalProvId from PortalProviders p2 where p2.providerHcsid in
			          (select f.uploadUid from EprfProvPortalFileUpload f where f.fileType in (:fileType1, :fileType2)))
			   or p.portalProvId in (select m.portalProvId from PortalProviderUserMapping m where m.userHcsid in
			          (select f.uploadUid from EprfProvPortalFileUpload f where f.fileType in (:fileType1, :fileType2)))
			""")
	List<PortalProviderNameRow> findSubmittedProviders(@Param("fileType1") String fileType1, @Param("fileType2") String fileType2);
}
