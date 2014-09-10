package org.springframework.social.instagram.api;

import java.util.Map;

public class InstagramProfile {
	
	private final long id;
	private final String profilePictureUrl;
	private final String username;
	private final Map<String,Integer> counts;
	private final String fullName;
	private String bio;
	private String website;
	private String firstName;
	private String lastName;
	
	public InstagramProfile(long id, String username, String fullName, String profilePictureUrl, Map<String,Integer> counts) {
		this.id = id;
		this.username = username;
		this.fullName = fullName;
		this.profilePictureUrl = profilePictureUrl;
		this.counts = counts;
	}
	
	public long getId() {
		return id;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}
	
	public String getUsername() {
		return username;
	}

	public Map<String,Integer> getCounts() {
		return counts;
	}	
	
	public int getMediaCount() {
		if(counts!=null && counts.containsKey("follows")){
			return counts.get("media");
		}
		return 0;
			
	}
	
	public int getFollowsCount() {
		if(counts!=null && counts.containsKey("follows")){
			return counts.get("follows");
		}
		return 0;
	}
	
    public int getFollowedBy() {
    	if(counts!=null && counts.containsKey("follows")){
    		return counts.get("followed_by");
		}
		return 0;
    		
    }

	public String getBio() {
		return bio;
	}

	public String getWebsite() {
		return website;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
}
