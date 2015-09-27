package de.adorsys.tanserver.repository;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import de.adorsys.tanserver.model.DeviceForAccount;

public class DeviceForAccountRepository extends BasicDAO<DeviceForAccount, String> {

	public DeviceForAccountRepository(Class<DeviceForAccount> entityClass, Datastore ds) {
		super(entityClass, ds);
	}

}
