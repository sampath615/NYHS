package gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/** The earlier versions of the system messages (view NYEHMS_SYSTEM_MSG_HISTORY_VW); read-only, read through projections (see {@link SystemMessageViewColumns}). */
@Entity
@Immutable
@Table(name = "NYEHMS_SYSTEM_MSG_HISTORY_VW")
public class SystemMessageHistoryView extends SystemMessageViewColumns {
}
