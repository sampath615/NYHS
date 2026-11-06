package gov.ny.state.doh.bhae.nyehms.web.spring.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.common.RawAddrCanbeRes;

/** The provider / facility addresses that can be a residence (RAW_ADDR_CANBE_RES). */
public interface RawAddrCanbeResRepository extends JpaRepository<RawAddrCanbeRes, Long> {
}
