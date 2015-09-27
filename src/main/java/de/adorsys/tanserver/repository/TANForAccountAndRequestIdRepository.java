package de.adorsys.tanserver.repository;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import de.adorsys.tanserver.model.TANForAccountAndRequestId;

public class TANForAccountAndRequestIdRepository extends BasicDAO<TANForAccountAndRequestId, ObjectId> {

	public TANForAccountAndRequestIdRepository(Class<TANForAccountAndRequestId> entityClass, Datastore ds) {
		super(entityClass, ds);
	}

}
