package gov.ny.state.doh.bhae.nyehms.web.spring.dao.document;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.TRKAddressDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.domain.document.TRKAliasDocVO;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.TrackingUserEnteredAlias;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.TrackingUserEnteredAddress;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.TrackingAddressRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.record.TrackingAliasRow;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.common.TrackingUserEnteredAliasRepository;
import gov.ny.state.doh.bhae.nyehms.web.spring.repository.document.TrackingUserEnteredAddressRepository;
import lombok.RequiredArgsConstructor;

/**
 * The tracking documents (an address or an alias entered in the tracking system) for their document screens (formerly
 * a MyBatis mapper with the same name and methods), through Spring Data repositories over TrackingUserEnteredAddress,
 * TrackingUserEnteredAlias and NyehmsUsers. No native SQL: the worker's name is the database function
 * PKG_TRK_COMMON.fn_get_fullname, called from JPQL.
 * <p>
 * CREATEID of the VOs is that name (not the CREATEID column), as before.
 *
 * @author ska03
 */
@Repository
@RequiredArgsConstructor
public class TrackingDocumentDAO {

	private final TrackingUserEnteredAddressRepository addresses;
	private final TrackingUserEnteredAliasRepository aliases;

	/**
	 * The tracking address of a document (any one row if there are several).
	 *
	 * @param docId the document id
	 * @return the address, or null if there is none
	 */
	public TRKAddressDocVO getTRKAddressDoc(int docId) throws DataAccessException {
		List<TrackingAddressRow> rows = addresses.findFirstWithCreator(docId);
		return rows.isEmpty() ? null : toAddressVO(rows.get(0));
	}

	/**
	 * The tracking alias of a document (any one row if there are several).
	 *
	 * @param docId the document id
	 * @return the alias, or null if there is none
	 */
	public TRKAliasDocVO getTRKAliasDoc(int docId) {
		List<TrackingAliasRow> rows = aliases.findFirstWithCreator(docId);
		return rows.isEmpty() ? null : toAliasVO(rows.get(0));
	}

	/** The VO as MyBatis filled it: only the values that are not null are set; a row without values is null. */
	private static TRKAddressDocVO toAddressVO(TrackingAddressRow row) {
		TrackingUserEnteredAddress a = row.address();
		TRKAddressDocVO vo = new TRKAddressDocVO();
		boolean set = false;
		if (a.getDocumentId() != null) { vo.setDOCUMENT_ID(a.getDocumentId()); set = true; }
		if (a.getStateno() != null) { vo.setSTATENO(a.getStateno()); set = true; }
		if (a.getFname() != null) { vo.setFNAME(a.getFname()); set = true; }
		if (a.getMname() != null) { vo.setMNAME(a.getMname()); set = true; }
		if (a.getLname() != null) { vo.setLNAME(a.getLname()); set = true; }
		if (a.getSsn() != null) { vo.setSSN(a.getSsn()); set = true; }
		if (a.getDobText() != null) { vo.setDOB_TEXT(a.getDobText()); set = true; }
		if (a.getSuffix() != null) { vo.setSUFFIX(a.getSuffix()); set = true; }
		if (a.getAddress() != null) { vo.setADDRESS(a.getAddress()); set = true; }
		if (a.getAddressCity() != null) { vo.setADDRESS_CITY(a.getAddressCity()); set = true; }
		if (a.getAddressState() != null) { vo.setADDRESS_STATE(a.getAddressState()); set = true; }
		if (a.getAddressZipcode() != null) { vo.setADDRESS_ZIPCODE(a.getAddressZipcode()); set = true; }
		if (a.getAddressPlus4() != null) { vo.setADDRESS_PLUS4(a.getAddressPlus4()); set = true; }
		if (a.getCountyFips() != null) { vo.setCOUNTY_FIPS(a.getCountyFips()); set = true; }
		if (a.getHomePhone() != null) { vo.setHOME_PHONE(a.getHomePhone()); set = true; }
		if (a.getAddressTractCensus() != null) { vo.setADDRESS_TRACT_CENSUS(a.getAddressTractCensus()); set = true; }
		if (a.getTractSuffixCensus() != null) { vo.setTRACT_SUFFIX_CENSUS(a.getTractSuffixCensus()); set = true; }
		if (a.getAddressLatitude() != null) { vo.setADDRESS_LATITUDE(a.getAddressLatitude()); set = true; }
		if (a.getAddressLongitude() != null) { vo.setADDRESS_LONGITUDE(a.getAddressLongitude()); set = true; }
		if (row.createdByName() != null) { vo.setCREATEID(row.createdByName()); set = true; }
		if (a.getCreatedt() != null) { vo.setCREATEDT(a.getCreatedt()); set = true; }
		return set ? vo : null;
	}

	/** The VO as MyBatis filled it: only the values that are not null are set; a row without values is null. */
	private static TRKAliasDocVO toAliasVO(TrackingAliasRow row) {
		TrackingUserEnteredAlias a = row.alias();
		TRKAliasDocVO vo = new TRKAliasDocVO();
		boolean set = false;
		if (a.getDocumentId() != null) { vo.setDOCUMENT_ID(a.getDocumentId()); set = true; }
		if (a.getAliasId() != null) { vo.setALIAS_ID(a.getAliasId()); set = true; }
		if (a.getStateno() != null) { vo.setSTATENO(a.getStateno()); set = true; }
		if (a.getRepsrccd() != null) { vo.setREPSRCCD(a.getRepsrccd()); set = true; }
		if (a.getAliasNo() != null) { vo.setALIAS_NO(a.getAliasNo()); set = true; }
		if (a.getFname() != null) { vo.setFNAME(a.getFname()); set = true; }
		if (a.getMname() != null) { vo.setMNAME(a.getMname()); set = true; }
		if (a.getLname() != null) { vo.setLNAME(a.getLname()); set = true; }
		if (a.getSsn() != null) { vo.setSSN(a.getSsn()); set = true; }
		if (a.getDobText() != null) { vo.setDOB_TEXT(a.getDobText()); set = true; }
		if (a.getDob() != null) { vo.setDOB(a.getDob()); set = true; }
		if (a.getSuffix() != null) { vo.setSUFFIX(a.getSuffix()); set = true; }
		if (row.createdByName() != null) { vo.setCREATEID(row.createdByName()); set = true; }
		if (a.getCreatedt() != null) { vo.setCREATEDT(a.getCreatedt()); set = true; }
		if (a.getUpdateUid() != null) { vo.setUPDATE_UID(a.getUpdateUid()); set = true; }
		if (a.getUpdateDt() != null) { vo.setUPDATE_DT(a.getUpdateDt()); set = true; }
		if (a.getComments() != null) { vo.setCOMMENTS(a.getComments()); set = true; }
		return set ? vo : null;
	}
}
