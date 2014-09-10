package org.springframework.social.instagram.api.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.List;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.social.instagram.api.InstagramProfile;
import org.springframework.social.instagram.api.PagedMediaList;
import org.springframework.social.instagram.api.Relationship;

public class UserTemplateTest extends AbstractInstagramApiTest {

	@Test
	public void getUser() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/users/self/?access_token=ACCESS_TOKEN"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/user-profile.json"), MediaType.APPLICATION_JSON));
		
		InstagramProfile user = instagram.userOperations().getUser();
		assertEquals("tomharman", user.getUsername());
		mockServer.verify();
	}
	
	@Test
	public void getSpecificUser() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/users/12345/?access_token=ACCESS_TOKEN"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/user-profile.json"), MediaType.APPLICATION_JSON));
		
		InstagramProfile user = instagram.userOperations().getUser(12345);
		assertEquals("tomharman", user.getUsername());
		mockServer.verify();
	}
	
	@Test
	public void getFeed() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/users/self/feed/?access_token=ACCESS_TOKEN"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/recent-media.json"), MediaType.APPLICATION_JSON));
		
		PagedMediaList media = instagram.userOperations().getFeed();
		assertPagedResults(media);
		mockServer.verify();
	}
	
	@Test
	public void getRecentMedia() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/users/12345/media/recent/?access_token=ACCESS_TOKEN"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/recent-media.json"), MediaType.APPLICATION_JSON));
	
		PagedMediaList media = instagram.userOperations().getRecentMedia(12345);
		assertPagedResults(media);
		mockServer.verify();
	}
	
	@Test
	public void getFollowedBy() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/users/12345/followed-by/?access_token=ACCESS_TOKEN"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/user-list.json"), MediaType.APPLICATION_JSON));
		
		List<InstagramProfile> follows = instagram.userOperations().getFollowedBy(12345);
		assertTrue(follows.size() > 0);
		mockServer.verify();
	}
	
	@Test
	public void getFollows() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/users/12345/follows/?access_token=ACCESS_TOKEN"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/user-list.json"), MediaType.APPLICATION_JSON));
		
		List<InstagramProfile> follows = instagram.userOperations().getFollows(12345);
		assertTrue(follows.size() > 0);
		mockServer.verify();
	}
	
	@Test
	public void getRequestedBy() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/users/self/requested-by/?access_token=ACCESS_TOKEN"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/user-list.json"), MediaType.APPLICATION_JSON));
		
		List<InstagramProfile> follows = instagram.userOperations().getRequestedBy();
		assertTrue(follows.size() > 0);
		mockServer.verify();
	}
	
	@Test
	public void getRelationship() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/users/12345/relationship/?access_token=ACCESS_TOKEN"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("testdata/relationship.json"), MediaType.APPLICATION_JSON));
	
		Relationship relationship = instagram.userOperations().getRelationship(12345);
		assertNotNull(relationship.getIncomingStatus());
		assertNotNull(relationship.getOutgoingStatus());
		mockServer.verify();
	}
	
	@Test
	public void followUser() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/users/12345/relationship/?access_token=ACCESS_TOKEN"))
			.andExpect(method(POST))
			//.andExpect(content()body("action=follow"))
			.andRespond(withSuccess(jsonResource("testdata/media-list.json"), MediaType.APPLICATION_JSON));
		
		instagram.userOperations().followUser(12345);
		mockServer.verify();
	}
	
	@Test
	public void unfollowUser() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/users/12345/relationship/?access_token=ACCESS_TOKEN"))
			.andExpect(method(POST))
			//.andExpect(body("action=unfollow"))
			.andRespond(withSuccess(jsonResource("testdata/ok-response.json"), MediaType.APPLICATION_JSON));

		instagram.userOperations().unfollowUser(12345);
		mockServer.verify();
	}
	
	@Test
	public void blockUser() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/users/12345/relationship/?access_token=ACCESS_TOKEN"))
			.andExpect(method(POST))
			.andExpect(content().string("action=block"))
			.andRespond(withSuccess(jsonResource("testdata/ok-response.json"), MediaType.APPLICATION_JSON));
		
		instagram.userOperations().blockUser(12345);
		mockServer.verify();
	}
	
	@Test
	public void unblockUser() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/users/12345/relationship/?access_token=ACCESS_TOKEN"))
			.andExpect(method(POST))
			.andExpect(content().string("action=unblock"))
			.andRespond(withSuccess(jsonResource("testdata/ok-response.json"), MediaType.APPLICATION_JSON));

		instagram.userOperations().unblockUser(12345);
		mockServer.verify();
	}
	
	@Test
	public void approveUser() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/users/12345/relationship/?access_token=ACCESS_TOKEN"))
			.andExpect(method(POST))
			.andExpect(content().string("action=approve"))
			.andRespond(withSuccess(jsonResource("testdata/ok-response.json"), MediaType.APPLICATION_JSON));

		instagram.userOperations().approveUser(12345);
		mockServer.verify();
	}
	
	@Test
	public void denyUser() {
		mockServer.expect(requestTo("https://api.instagram.com/v1/users/12345/relationship/?access_token=ACCESS_TOKEN"))
			.andExpect(method(POST))
			.andExpect(content().string("action=deny"))
			.andRespond(withSuccess(jsonResource("testdata/ok-response.json"), MediaType.APPLICATION_JSON));

		instagram.userOperations().denyUser(12345);
		mockServer.verify();
	}
}
