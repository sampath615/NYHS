package gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

/** View NYEHMS_ACTIVE_SYSTEM_MSG_VW: not deleted and not yet ended (running now or starting later). */
@Entity
@Immutable
@Table(name = "NYEHMS_ACTIVE_SYSTEM_MSG_VW")
public class ActiveSystemMessage extends SystemMessageListRow {

    protected ActiveSystemMessage() {
    }

    @Override
    public String getFlagDeleted() {
        return null;   // the active view has no FLAG_DELETED column
    }
}
