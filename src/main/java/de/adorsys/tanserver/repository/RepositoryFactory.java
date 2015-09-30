/**
 * Copyright (C) 2015 Sandro Sonntag (sso@adorsys.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.adorsys.tanserver.repository;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import de.adorsys.tanserver.SystemSettings;
import de.adorsys.tanserver.model.DeviceForAccount;
import de.adorsys.tanserver.model.TANForAccountAndRequestId;

@Singleton
public class RepositoryFactory {
	
	private MongoClient client = new MongoClient(new MongoClientURI(SystemSettings.MONGODB_URL));
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
