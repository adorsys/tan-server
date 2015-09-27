package de.adorsys.tanserver.rest;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;

@RunWith(Arquillian.class)
public class BaseARTTest {

	private static final String WEBAPP_SRC = "src/main/webapp";
	private static final Logger LOG = LoggerFactory.getLogger(BaseARTTest.class);
	
	@ArquillianResource
	URL contextRoot;
	String contextRootPath;

	@Deployment(testable = false, name = "test.war")
	public static WebArchive create() {
		File[] libs = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve().withTransitivity().asFile();
		WebArchive res = ShrinkWrap.create(WebArchive.class, "tan-server.war");
	    for(File file : libs){
	        res.addAsLibrary(file);
	    }       
	    res.addPackages(true, "de.adorsys.tanserver");
	    res.setWebXML(new File(WEBAPP_SRC, "WEB-INF/web.xml"));
	    res.addAsWebResource(new File(WEBAPP_SRC, "WEB-INF/beans.xml"), "WEB-INF/beans.xml");
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
	
	}

}