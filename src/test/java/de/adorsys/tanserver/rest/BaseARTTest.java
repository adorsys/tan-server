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
package de.adorsys.tanserver.rest;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.config.ObjectMapperConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.internal.mapper.ObjectMapperType;

@RunWith(Arquillian.class)
public class BaseARTTest {

	private static final String WEBAPP_SRC = "src/main/webapp";
	private static final Logger LOG = LoggerFactory.getLogger(BaseARTTest.class);
	
	@ArquillianResource
	URL contextRoot;
	String contextRootPath;

	@Deployment(testable = false, name = "test.war")
	public static WebArchive create() {
		File[] libs = Maven.resolver().loadPomFromFile("pom.xml").importDependencies(ScopeType.COMPILE, ScopeType.RUNTIME).resolve().withTransitivity().asFile();
		ArrayList<File> libFiles = new ArrayList<>(Arrays.asList(libs));
		Iterator<File> iterator = libFiles.iterator();
		while (iterator.hasNext()) {
			File type = iterator.next();
			if (type.toString().contains("slf4j")) {
				iterator.remove();
			}
		}
		
		WebArchive res = ShrinkWrap.create(WebArchive.class, "tan-server.war");
	    for(File file : libFiles){
	        res.addAsLibrary(file);
	    }       
	    res.addPackages(true, "de.adorsys.tanserver");
	    res.setWebXML(new File(WEBAPP_SRC, "WEB-INF/web.xml"));
	    res.addAsWebResource(new File(WEBAPP_SRC, "WEB-INF/beans.xml"), "WEB-INF/beans.xml");
	    res.addAsManifestResource("MANIFEST.MF");
	    // Show the deploy structure
	    LOG.debug(res.toString(true)); 
		return res;
	
	}

	@Before
	public void before() {
		contextRootPath = contextRoot.toString().replaceAll("0.0.0.0", "docker");
		LOG.debug("deployment url: {}", contextRootPath);
		RestAssured.baseURI = contextRootPath;
		RestAssured.basePath = "/rest";
		RestAssured.requestSpecification = new RequestSpecBuilder().setAccept(ContentType.JSON).setContentType(ContentType.JSON).build();
		RestAssured.config = RestAssured.config().objectMapperConfig(ObjectMapperConfig.objectMapperConfig().defaultObjectMapperType(ObjectMapperType.GSON));
	}

}