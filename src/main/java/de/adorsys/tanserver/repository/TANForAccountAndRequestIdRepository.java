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

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import com.mongodb.WriteResult;

import de.adorsys.tanserver.model.TANForAccountAndRequestId;

public class TANForAccountAndRequestIdRepository extends BasicDAO<TANForAccountAndRequestId, ObjectId> {

	public TANForAccountAndRequestIdRepository(Class<TANForAccountAndRequestId> entityClass, Datastore ds) {
		super(entityClass, ds);
	}

	public boolean deleteTAN(String accountId, String requestId, String activationTAN) {
		WriteResult result = deleteByQuery(getDatastore().createQuery(TANForAccountAndRequestId.class).filter("accountId", accountId).filter("requestId", requestId).filter("tan", activationTAN));
		return result.getN() > 0;
	}

}
