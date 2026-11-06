package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.CodedPsMsgType;

/** The partner services message types (table CODED_PS_MSG_TYPE). */
public interface CodedPsMsgTypeRepository extends JpaRepository<CodedPsMsgType, String> {

	/** A message type with its description (a projection: a type that repeats gives several rows, as before). */
	record MessageType(String psMsgType, String psMsgTypeDescription) {
	}

	/** The types in use (no END_DATE), by description. */
	List<MessageType> findByEndDateIsNullOrderByPsMsgTypeDescription();
}
