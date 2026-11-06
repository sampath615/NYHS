package gov.ny.state.doh.bhae.nyehms.web.spring.entity.lab;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import lombok.Getter;

/**
 * The communications with a lab (table LAB_INTERACTION_HISTORY): date, worker, communication type and notes (a CLOB).
 * <p>
 * Inserted by JPQL inserts, read through projections. <b>INTERACTION_ID as the key, filled by the database on insert (the former inserts did not set it), is assumed.</b>
 */
@Entity
@Getter
@Table(name = "LAB_INTERACTION_HISTORY")
public class LabInteractionHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "INTERACTION_ID")
	private Long interactionId;

	@Column(name = "PFI")
	private String pfi;

	@Column(name = "INTERACTION_DATE")
	private Timestamp interactionDate;

	@Column(name = "INTERACTION_BY")
	private Long interactionBy;

	@Column(name = "COMMUNICATION_TYPE")
	private String communicationType;

	@Lob
	@Column(name = "INTERACTION_NOTES")
	private String interactionNotes;
}
