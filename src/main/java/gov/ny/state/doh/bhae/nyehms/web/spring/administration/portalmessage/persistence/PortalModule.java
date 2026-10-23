package gov.ny.state.doh.bhae.nyehms.web.spring.administration.portalmessage.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

/** Provider Portal modules a notice can target (lookup table PROVIDER_PORTAL_MODULES). Read-only. */
@Entity
@Immutable
@Table(name = "PROVIDER_PORTAL_MODULES")
public class PortalModule {

    @Id
    @Column(name = "PP_MODULE_ID")
    private Long id;

    @Column(name = "PP_MODULE_CD")
    private String code;

    @Column(name = "PP_MODULE_NAME")
    private String name;

    protected PortalModule() {
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
}
