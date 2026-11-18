package gov.ny.state.doh.bhae.nyehms.web.spring.administration.systemmessage.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

/** View NYEHMS_INACTIVE_SYSTEM_MSG_VW: deleted or already ended. */
@Entity
@Immutable
@Table(name = "NYEHMS_INACTIVE_SYSTEM_MSG_VW")
public class InactiveSystemMessage extends SystemMessageListRow {

    @Column(name = "FLAG_DELETED")
    private String flagDeleted;

    protected InactiveSystemMessage() {
    }

    @Override
    public String getFlagDeleted() {
        return flagDeleted;
    }
}
