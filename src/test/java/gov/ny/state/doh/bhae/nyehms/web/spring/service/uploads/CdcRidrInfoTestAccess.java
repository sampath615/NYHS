package gov.ny.state.doh.bhae.nyehms.web.spring.service.uploads;

import java.util.Map;

import gov.ny.state.doh.bhae.nyehms.web.spring.entity.upload.CdcRidrInfo;

/** Test access to the package-private {@link CdcRidrInfoMapping}. */
public final class CdcRidrInfoTestAccess {

	private CdcRidrInfoTestAccess() {
	}

	public static CdcRidrInfo toRow(Map<String, Object> row, String fileName, int workerId) {
		return CdcRidrInfoMapping.toRow(row, fileName, workerId);
	}
}
