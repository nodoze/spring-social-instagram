package org.springframework.social.instagram.api.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.List;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.social.instagram.api.Comment;
import org.springframework.social.instagram.api.InstagramProfile;
import org.springframework.social.instagram.api.Media;

public class MediaTemplateTest extends AbstractInstagramApiTest {
	
	@Test
	public void getMedia() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/media/48904105/?access_token=ACCESS_TOKEN"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/media.json"), MediaType.APPLICATION_JSON));
		
		Media media = instagram.mediaOperations().getMedia(48904105);
		assertEquals(48904105, media.getId());
		assertTrue(media.getTags().contains("cat") && media.getTags().contains("food"));
		mockServer.verify();
	}
	
	@Test
	public void getPopular() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/media/popular/?access_token=ACCESS_TOKEN"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/media-list.json"), MediaType.APPLICATION_JSON));
		
		List<Media> media = instagram.mediaOperations().getPopular();
		assertTrue(media.size() > 0);
		mockServer.verify();
	}
	
	@Test
	public void search() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/media/search/?access_token=ACCESS_TOKEN&lng=200.0&lat=100.0"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/media-list.json"), MediaType.APPLICATION_JSON));

		List<Media> media = instagram.mediaOperations().search(100d, 200d);
		assertTrue(media.size() > 0);
		mockServer.verify();
	}
	
	@Test
	public void searchMoreParams() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/media/search/?access_token=ACCESS_TOKEN&distance=1000&lng=200.0&lat=100.0"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/media-list.json"), MediaType.APPLICATION_JSON));
		
		List<Media> media = instagram.mediaOperations().search(100d, 200d, 1000);
		assertTrue(media.size() > 0);
		mockServer.verify();
	}
	
	@Test
	public void searchEvenMoreParams() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/media/search/?access_token=ACCESS_TOKEN&distance=1000&max_timestamp=1000&lng=200.0&min_timestamp=2000&lat=100.0"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/media-list.json"), MediaType.APPLICATION_JSON));
		
		List<Media> media = instagram.mediaOperations().search(100d, 200d, 1000l, 2000l, 1000);
		assertTrue(media.size() > 0);
		mockServer.verify();
	}
	
	@Test
	public void getLikes() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/media/12345/likes/?access_token=ACCESS_TOKEN"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/user-list.json"), MediaType.APPLICATION_JSON));
	
		List<InstagramProfile> likes = instagram.mediaOperations().getLikes(12345);
		assertTrue(likes.size() > 0);
		mockServer.verify();
	}
	
	@Test
	public void getComments() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/media/12345/comments/?access_token=ACCESS_TOKEN"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/comment-list.json"), MediaType.APPLICATION_JSON));
	
		List<Comment> comments = instagram.mediaOperations().getComments(12345);
		assertTrue(comments.size() > 0);
		mockServer.verify();
	}
	
	@Test
	public void addComment() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/media/12345/comments/?access_token=ACCESS_TOKEN"))
			.andExpect(method(POST))
			//.andExpect(body("text=Awesome%21"))
			.andRespond(withSuccess(jsonResource("testdata/ok-response.json"), MediaType.APPLICATION_JSON));
		
		instagram.mediaOperations().addComment(12345, "Awesome!");
		mockServer.verify();
	}
	
	@Test
	public void addLike() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/media/12345/likes/?access_token=ACCESS_TOKEN"))
			.andExpect(method(POST))
			.andRespond(withSuccess(jsonResource("testdata/ok-response.json"), MediaType.APPLICATION_JSON));
		
		instagram.mediaOperations().addLike(12345);
		mockServer.verify();
	}
	
	@Test
	public void deleteComment() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/media/12345/comments/54321/?access_token=ACCESS_TOKEN"))
			.andExpect(method(DELETE))
			.andRespond(withSuccess(jsonResource("testdata/ok-response.json"), MediaType.APPLICATION_JSON));
		
		instagram.mediaOperations().deleteComment(12345, 54321);
		mockServer.verify();
	}
	
	@Test
	public void deleteLike() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/media/12345/likes/?access_token=ACCESS_TOKEN"))
			.andExpect(method(DELETE))
			.andRespond(withSuccess(jsonResource("testdata/ok-response.json"), MediaType.APPLICATION_JSON));

		instagram.mediaOperations().deleteLike(12345);
		mockServer.verify();
	}
}
