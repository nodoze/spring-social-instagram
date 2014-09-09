package org.springframework.social.instagram.connect;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.instagram.api.Instagram;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

public class InstagramConnectionFactory extends OAuth2ConnectionFactory<Instagram> {

	public InstagramConnectionFactory(String clientId, String clientSecret) {
		super("instagram", new InstagramServiceProvider(clientId, clientSecret), new InstagramAdapter());
	}
	
	@Override
	protected String extractProviderUserId(AccessGrant accessGrant) {
		Instagram api = ((InstagramServiceProvider)getServiceProvider()).getApi(accessGrant.getAccessToken());
	    UserProfile userProfile = getApiAdapter().fetchUserProfile(api);
	    return userProfile.getUsername();
	}

	/**
	 * Create a OAuth2-based {@link Connection} from the {@link AccessGrant} returned after {@link #getOAuthOperations() completing the OAuth2 flow}.
	 * @param accessGrant the access grant
	 * @return the new service provider connection
	 * @see OAuth2Operations#exchangeForAccess(String, String, org.springframework.util.MultiValueMap)
	 */
	@Override
	public Connection<Instagram> createConnection(AccessGrant accessGrant) {
		try{
		return new OAuth2Connection<Instagram>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
				accessGrant.getRefreshToken(), accessGrant.getExpireTime(), 
				(OAuth2ServiceProvider<Instagram>) getServiceProvider(), getApiAdapter());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


}
