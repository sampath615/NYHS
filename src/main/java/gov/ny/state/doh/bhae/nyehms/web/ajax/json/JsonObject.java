package gov.ny.state.doh.bhae.nyehms.web.ajax.json;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

@Component  
@XmlRootElement
public class JsonObject implements Serializable{
	
	private List resultList;

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}
	
}
