package gov.ny.state.doh.bhae.nyehms.web.spring.record;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.RawNycprfPartner;
import gov.ny.state.doh.bhae.nyehms.web.spring.entity.document.StdNycprfPartner;

public record NycPrfPartnerData(StdNycprfPartner stdNycprfPartner,
                                RawNycprfPartner rawNycprfPartner) {
}
