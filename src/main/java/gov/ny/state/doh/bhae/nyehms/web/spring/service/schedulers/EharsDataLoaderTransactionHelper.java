package gov.ny.state.doh.bhae.nyehms.web.spring.service.schedulers;


import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class EharsDataLoaderTransactionHelper {

	private static Logger log = LoggerFactory.getLogger(EharsDataLoaderTransactionHelper.class);

	@Autowired private EharsDataLoaderService    eharsDataLoaderService;

	@Transactional
	public void loadEharsDataInToNyehmsTables(List<String> notificationObj)throws SQLException {

		
		eharsDataLoaderService.loadRidrDataToNYEHMS(notificationObj);
		
		//eharsDataLoaderService.loadRidrDataToNYEHMS(sqlDataResultSet, notificationObj);
		
	}
}
