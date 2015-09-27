package de.adorsys.tanserver.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexes;

@Entity(value="tans", noClassnameStored=true)
@Indexes({
	@Index(fields={@Field("accountId"), @Field("requestId")}, options = @IndexOptions(unique=true)),
	@Index(fields={@Field("timestamp")}, options = @IndexOptions(unique=true, expireAfterSeconds=60))
})
public class TANForAccountAndRequestId {
	@Id 
	ObjectId id;
	String accountId;
	String requestId;
	String tan;
	Date timestamp;
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getTan() {
		return tan;
	}
	public void setTan(String tan) {
		this.tan = tan;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
}
