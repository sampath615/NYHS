package gov.ny.state.doh.bhae.nyehms.web.spring.initialize.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

@Entity                                        // "this class is a table"
@Table(name = "CODED_INIT_FAILURE_REASONS")    // which table
@Immutable                                     // read-only: Hibernate never updates it
public class InitFailureReason {
    @Id                                        // primary key
    @Column(name = "REASON_CD")
    private Integer reasonCd;
    @Column(name = "SHORT_DESC")
    private String shortDesc;
    @Column(name = "DESCRIPTION")
    private String description;
    protected InitFailureReason() { }          // JPA needs a no-arg constructor
    public Integer getReasonCd()   { return reasonCd; }
    public String getShortDesc()   { return shortDesc; }
    public String getDescription() { return description; }
}