package de.adorsys.tanserver.repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

import de.adorsys.tanserver.model.DeviceForAccount;
import de.adorsys.tanserver.model.TANForAccountAndRequestId;

@Singleton
public class RepositoryFactory {
	
	@Resource(lookup="java:global/mongoClient")
	private MongoClient client;
	private Datastore datastore;
	
	@PostConstruct
	private void setup() {
		datastore = new Morphia().map(DeviceForAccount.class, TANForAccountAndRequestId.class).createDatastore(client, "tanserver");
		datastore.ensureIndexes();
	}
	
	
	@Produces
	@Singleton
	public DeviceForAccountRepository createDeviceRepo() {
		return new DeviceForAccountRepository(DeviceForAccount.class, datastore);
	}
	
	@Produces
	@Singleton
	public TANForAccountAndRequestIdRepository createTANRepo() {
		return new TANForAccountAndRequestIdRepository(TANForAccountAndRequestId.class, datastore);
	}

}
