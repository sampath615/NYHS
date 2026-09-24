package gov.ny.state.doh.bhae.nyehms.webservice.webclient.filter;

import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

/**
 * Provides static methods which return filter functions that can be attached to a 
 * <code>WebClient</code> or a <code>WebClient.Builder</code>.  
 *
 */


public class FilterFactory {

	/**
	 * Returns a filter that will check a client response for a HTTP status code in the 400 (client error) 
	 * or 500 (internal server error) range and throw a WebClientException if one is found.
	 * 
	 * <p><b>Note:</b> This filter is only needed if you are using the <code>exchange</code>
	 * method of the webclient, since the <code>retrieve</code> method automatically throws a
	 * WebClientException if there is a 400 or 500 status code. However, if it is added then 
	 * this filter will be triggered by <code>retrieve</code> instead of the default behavior.</p> 
	 */
	public static ExchangeFilterFunction errorHandlingFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
        	if(clientResponse.statusCode() != null && (clientResponse.statusCode().is5xxServerError() || clientResponse.statusCode().is4xxClientError()) ) {
        		return  clientResponse. createException().flatMap(e -> Mono.error(e));
        	
            }
            else {
                return Mono.just(clientResponse);
            }
        });
    }
	//Please note that bodyToMono() and bodyToFlux() methods always expect a response body of a given class type. 
	//If the response status code is 4xx (client error) or 5xx (Server error) i.e. there is no response body then these methods throw WebClientException.
	
	
	/**
	 * Returns a filter that will treat any HTTP status code that isn't in the 200 (successful)
	 * range as an error by throwing a WebClientException.
	 */
	public static ExchangeFilterFunction successfulOnlyFilter() {
		return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
        	if(clientResponse.statusCode() != null && clientResponse.statusCode().is2xxSuccessful()) {
        		return Mono.just(clientResponse);
        	}
        	else {
        		return clientResponse.createException().flatMap(e -> Mono.error(e));
        	}
		});
	}
}
