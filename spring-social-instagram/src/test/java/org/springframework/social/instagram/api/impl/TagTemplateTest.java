package org.springframework.social.instagram.api.impl;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.List;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.social.instagram.api.PagedMediaList;
import org.springframework.social.instagram.api.Tag;

@SuppressWarnings("unused")
public class TagTemplateTest extends AbstractInstagramApiTest {

	@Test
	public void getTag() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/tags/cats/?access_token=ACCESS_TOKEN"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/tag.json"), MediaType.APPLICATION_JSON));
		
		Tag tag = instagram.tagOperations().getTag("cats");
		mockServer.verify();
	}
	
	@Test
	public void searchTags() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/tags/search/?access_token=ACCESS_TOKEN&q=cats"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/tag-search.json"), MediaType.APPLICATION_JSON));
		
		List<Tag> tags = instagram.tagOperations().search("cats");
		mockServer.verify();
	}
	
	@Test
	public void recentMedia() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/tags/cats/media/recent/?access_token=ACCESS_TOKEN"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/recent-media.json"), MediaType.APPLICATION_JSON));
		
		PagedMediaList media = instagram.tagOperations().getRecentMedia("cats");
		assertPagedResults(media);
		mockServer.verify();
	}
	
	@Test
	public void recentMediaParams() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/tags/cats/media/recent/?access_token=ACCESS_TOKEN&max_id=100&min_id=200"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/recent-media.json"), MediaType.APPLICATION_JSON));
		
		PagedMediaList media = instagram.tagOperations().getRecentMedia("cats", 100, 200);
		assertPagedResults(media);
		mockServer.verify();
	}
	
}
