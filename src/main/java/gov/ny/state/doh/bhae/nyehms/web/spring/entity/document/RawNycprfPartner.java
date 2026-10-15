package gov.ny.state.doh.bhae.nyehms.web.spring.entity.document;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * A partner as received on an NYC Partner Referral Form (table RAW_NYCPRF_PARTNER); only the
 * columns the application reads.
 * <p>
 * Read-only. The document screen reads it through a JPQL projection; the import loads the entities
 * of a document. The key is PARTNERID: the import copies it to STD_NYCPRF_PARTNER.PARTNRID, which
 * is unique, so a partner received twice on a document still loads as two entities.
 */

@Entity
@Immutable
@Getter
@Table(name = "RAW_NYCPRF_PARTNER")
public class RawNycprfPartner {

	@Id
	@Column(name = "PARTNERID")
	private String partnerid;

	@Column(name = "PATIENTID")
	private String patientId;

	@Column(name = "NYCPRF_DOCUMENT_ID")
	private Long nycprfDocumentId;

	@Column(name = "PRTADDRESS")
	private String address;

	// read by the import (NycprfPartnerRepository.findReceivedPartnersOfDocument, NycPrfStdPartnerMapping)
	@Column(name = "CITYNO")
	private String cityno;

	@Column(name = "CODE")
	private String code;

	@Column(name = "DISEASE1")
	private String disease1;

	@Column(name = "DISEASE2")
	private String disease2;

	@Column(name = "HPRIORITY")
	private String hpriority;

	@Column(name = "INDEXCITYNO")
	private String indexcityno;

	@Column(name = "INTERV")
	private String interv;

	@Column(name = "NOTSEND")
	private String notsend;

	@Column(name = "P")
	private String p;

	@Column(name = "PRF_ELICITED")
	private String prfElicited;

	@Column(name = "PRF_HIVSTUS")
	private String prfHivstus;

	@Column(name = "PRF_SPOUSE")
	private String prfSpouse;

	@Column(name = "PRF_TSTDT")
	private String prfTstdt;

	@Column(name = "PRIDTINF")
	private String pridtinf;

	@Column(name = "PRINTED")
	private String printed;

	@Column(name = "PRIORITY")
	private String priority;

	@Column(name = "PRTAGE")
	private String prtage;

	@Column(name = "PRTALIAS")
	private String prtalias;

	@Column(name = "PRTASSISSTATUS")
	private String prtassisstatus;

	@Column(name = "PRTCITY")
	private String prtcity;

	@Column(name = "PRTCOMPLEX")
	private String prtcomplex;

	@Column(name = "PRTCPHONE")
	private String prtcphone;

	@Column(name = "PRTCURGENDER")
	private String prtcurgender;

	@Column(name = "PRTDOB")
	private String prtdob;

	@Column(name = "PRTDTHARSDX")
	private String prtdtharsdx;

	@Column(name = "PRTDTNOFITIED")
	private String prtdtnofitied;

	@Column(name = "PRTDVIOLENCEDONE")
	private String prtdviolencedone;

	@Column(name = "PRTEMAIL")
	private String prtemail;

	@Column(name = "PRTENTERDT")
	private String prtenterdt;

	@Column(name = "PRTFNAME")
	private String prtfname;

	@Column(name = "PRTHAIR")
	private String prthair;

	@Column(name = "PRTHEIWEI")
	private String prtheiwei;

	@Column(name = "PRTHPHONE")
	private String prthphone;

	@Column(name = "PRTHVDISPO")
	private String prthvdispo;

	@Column(name = "PRTHVDISPODT")
	private String prthvdispodt;

	@Column(name = "PRTLNAME")
	private String prtlname;

	@Column(name = "PRTMARITALSTUS")
	private String prtmaritalstus;

	@Column(name = "PRTMI")
	private String prtmi;

	@Column(name = "PRTPREGNANT")
	private String prtpregnant;

	@Column(name = "PRTRACE")
	private String prtrace;

	@Column(name = "PRTRSKOFDVIOLENCE")
	private String prtrskofdviolence;

	@Column(name = "PRTSEXBIRTH")
	private String prtsexbirth;

	@Column(name = "PRTSTATE")
	private String prtstate;

	@Column(name = "PRTZIP")
	private String prtzip;

	@Column(name = "REFERRAL")
	private String referral;

	@Column(name = "SOURCE")
	private String source;

	@Column(name = "TYPEINTERV")
	private String typeinterv;

	@Column(name = "TYPEREF")
	private String typeref;

	@Column(name = "UPDATED")
	private String updated;

	@Column(name = "WRKTEL")
	private String wrktel;
}
