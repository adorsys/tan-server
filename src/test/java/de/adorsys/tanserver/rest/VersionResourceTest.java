package de.adorsys.tanserver.rest;

import static com.jayway.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.restassured.RestAssured;

@RunWith(Arquillian.class)
public class VersionResourceTest {

	private static final String WEBAPP_SRC = "src/main/webapp";
	private static final Logger LOG = LoggerFactory.getLogger(VersionResourceTest.class);

	@ArquillianResource
	URL contextRoot;
	String contextRootPath;

	@Before
	public void before() {
		contextRootPath = contextRoot.toString().replaceAll("0.0.0.0", "docker");
		LOG.debug("deployment url: {}", contextRootPath);
		RestAssured.baseURI = contextRootPath;
		RestAssured.basePath = "/rest";

	}

	@Test
	public void should_be_deployed() {
	}

	@Deployment(testable = false, name="test.war")
	public static WebArchive create() {
		File[] libs = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve().withTransitivity().asFile();
		WebArchive res = ShrinkWrap.create(WebArchive.class);
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

	@Test
	public void should_parse_and_load_configuration_file(@ArquillianResource URL resource) throws IOException {
		given().expect().statusCode(200).when().get("/version");

	}
}
