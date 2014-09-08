package org.springframework.social.instagram.api.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.List;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.social.instagram.api.Location;
import org.springframework.social.instagram.api.PagedMediaList;

public class LocationTemplateTest extends AbstractInstagramApiTest {

	@Test
	public void getLocation() {
	    mockServer.expect(requestTo("https://api.instagram.com/v1/locations/12345/?access_token=ACCESS_TOKEN"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/location.json"), MediaType.APPLICATION_JSON));
	    
		Location location = instagram.locationOperations().getLocation(12345);
		assertEquals(37.782654745657382, location.getLatitude(), 0);
		mockServer.verify();
	}
	
	@Test 
	public void getRecentMedia() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/locations/12345/media/recent/?access_token=ACCESS_TOKEN"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/recent-media.json"), MediaType.APPLICATION_JSON));
		
		PagedMediaList media = instagram.locationOperations().getRecentMedia(12345);
		assertPagedResults(media);
		mockServer.verify();
	}
	
	@Test
	public void getRecentMediaParams() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/locations/12345/media/recent/?access_token=ACCESS_TOKEN&max_id=200&max_timestamp=10000&min_id=100&min_timestamp=5000"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/recent-media.json"), MediaType.APPLICATION_JSON));
		
		PagedMediaList media = instagram.locationOperations().getRecentMedia(12345, 200, 100, 5000, 10000);
		assertPagedResults(media);
		mockServer.verify();
	}
	
	@Test
	public void search() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/locations/search/?access_token=ACCESS_TOKEN&lng=200.0&lat=100.0"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/location-search.json"), MediaType.APPLICATION_JSON));
		
		List<Location> locations = instagram.locationOperations().search(100, 200);
		assertTrue(locations.size() > 0);
		mockServer.verify();
	}
	
	@Test
	public void searchWithDistance() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/locations/search/?access_token=ACCESS_TOKEN&distance=2000&lng=200.0&lat=100.0"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/location-search.json"), MediaType.APPLICATION_JSON));
		
		List<Location> locations = instagram.locationOperations().search(100, 200, 2000);
		assertTrue(locations.size() > 0);
		mockServer.verify();
	}
	
	@Test
	public void searcgWithFourSquare() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/locations/search/?access_token=ACCESS_TOKEN&foursquare_id=1000"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/location-search.json"), MediaType.APPLICATION_JSON));
		
		List<Location> locations = instagram.locationOperations().search(1000);
		assertTrue(locations.size() > 0);
		mockServer.verify();
	}
}
