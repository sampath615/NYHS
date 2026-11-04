package gov.ny.state.doh.bhae.nyehms.web.spring.entity.administration;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/** The inactive system messages (view NYEHMS_INACTIVE_SYSTEM_MSG_VW); read-only, read through projections (see {@link SystemMessageViewColumns}). */
@Entity
@Immutable
@Table(name = "NYEHMS_INACTIVE_SYSTEM_MSG_VW")
public class InactiveSystemMessageView extends SystemMessageViewColumns {
}
