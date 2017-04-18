package concord.appmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by aboieriu on 4/18/17.
 */
@Document
public class User {
	@Id
	private String id;
	private Long userId;
	private String username;
	private String firstname;
	private String lastname;
	private String domain;
	private String fullname;
	private String userpic_url;
	private Long photos_count;
	private Long galleries_count;
	private Long affection;
	private Long in_favorites_count;
	private Long friends_count;
	private Long followers_count;

	public User(){}

	public void merge(User user) {
		username = user.username;
		firstname = user.firstname;
		lastname = user.lastname;
		domain = user.domain;
		fullname = user.fullname;
		userpic_url = user.userpic_url;
		photos_count = user.photos_count;
		galleries_count = user.galleries_count;
		affection = user.affection;
		in_favorites_count = user.in_favorites_count;
		friends_count = user.friends_count;
		followers_count = user.followers_count;
	}

	@JsonCreator
	public User(
					@JsonProperty("id") Long userId,
					@JsonProperty("username") String username,
					@JsonProperty("firstname") String firstname,
					@JsonProperty("lastname") String lastname,
					@JsonProperty("domain") String domain,
					@JsonProperty("fullname") String fullname,
					@JsonProperty("userpic_url") String userpic_url,
					@JsonProperty("photos_count") Long photos_count,
					@JsonProperty("galleries_count") Long galleries_count,
					@JsonProperty("affection") Long affection,
					@JsonProperty("in_favorites_count") Long in_favorites_count,
					@JsonProperty("friends_count") Long friends_count,
					@JsonProperty("followers_count")  Long followers_count) {
		this.userId = userId;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.domain = domain;
		this.fullname = fullname;
		this.userpic_url = userpic_url;
		this.photos_count = photos_count;
		this.galleries_count = galleries_count;
		this.affection = affection;
		this.in_favorites_count = in_favorites_count;
		this.friends_count = friends_count;
		this.followers_count = followers_count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getUserpic_url() {
		return userpic_url;
	}

	public void setUserpic_url(String userpic_url) {
		this.userpic_url = userpic_url;
	}

	public Long getPhotos_count() {
		return photos_count;
	}

	public void setPhotos_count(Long photos_count) {
		this.photos_count = photos_count;
	}

	public Long getGalleries_count() {
		return galleries_count;
	}

	public void setGalleries_count(Long galleries_count) {
		this.galleries_count = galleries_count;
	}

	public Long getAffection() {
		return affection;
	}

	public void setAffection(Long affection) {
		this.affection = affection;
	}

	public Long getIn_favorites_count() {
		return in_favorites_count;
	}

	public void setIn_favorites_count(Long in_favorites_count) {
		this.in_favorites_count = in_favorites_count;
	}

	public Long getFriends_count() {
		return friends_count;
	}

	public void setFriends_count(Long friends_count) {
		this.friends_count = friends_count;
	}

	public Long getFollowers_count() {
		return followers_count;
	}

	public void setFollowers_count(Long followers_count) {
		this.followers_count = followers_count;
	}

	@Override
	public String toString() {
		return "User{" +
						"id='" + id + '\'' +
						", userId=" + userId +
						", username='" + username + '\'' +
						", firstname='" + firstname + '\'' +
						", lastname='" + lastname + '\'' +
						", domain='" + domain + '\'' +
						", fullname='" + fullname + '\'' +
						", userpic_url='" + userpic_url + '\'' +
						", photos_count=" + photos_count +
						", galleries_count=" + galleries_count +
						", affection=" + affection +
						", in_favorites_count=" + in_favorites_count +
						", friends_count=" + friends_count +
						", followers_count=" + followers_count +
						'}';
	}
}
