package org.drools.devguide.kieserver;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.naming.InitialContext;

import org.apache.commons.io.FileUtils;
import org.drools.core.runtime.impl.ExecutionResultImpl;
import org.drools.devguide.eshop.model.Item;
import org.drools.devguide.jaxb.JaxbItem;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.runtime.ExecutionResults;
import org.kie.internal.command.CommandFactory;
import org.kie.remote.client.jaxb.ClientJaxbSerializationProvider;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.ReleaseId;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;
import org.kie.services.client.serialization.JaxbSerializationProvider;

@RunWith(Arquillian.class)
public class JMSClientExampleTest {

	@Deployment
	public static WebArchive deployKieServer() throws Exception {
		String s = File.separator;
        String warFile = System.getProperty("user.home") + s + ".m2" 
        		+ s + "repository" + s + "org" + s + "drools" + s + "devguide" 
        		+ s + "kie-server-war" + s + "0.1-SNAPSHOT" 
        		+ s + "kie-server-war-0.1-SNAPSHOT-custom.war";
        File destFile = new File(System.getProperty("java.io.tmpdir") + s + "kie-server.war");
        FileUtils.copyFile(new File(warFile), destFile);
        return ShrinkWrap.createFromZipFile(WebArchive.class, destFile);
	}
	
	@Test
	@RunAsClient
	public void runSimpleRules() throws Exception {
		String USER = "testuser";
	    String PASSWORD = "test";
	    Set<Class<?>> extraJaxbClasses = new HashSet<Class<?>>(Arrays.asList(JaxbItem.class));
	    Properties initialProps = new Properties();
	    initialProps.setProperty(InitialContext.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
	    initialProps.setProperty(InitialContext.PROVIDER_URL, "remote://localhost:4447");
	    initialProps.setProperty(InitialContext.SECURITY_PRINCIPAL, USER);
	    initialProps.setProperty(InitialContext.SECURITY_CREDENTIALS, PASSWORD);
	    for (Object keyObj : initialProps.keySet()) {
	    	String key = (String) keyObj;
	    	System.setProperty(key, (String) initialProps.get(key));
	    }
	    InitialContext context = new InitialContext(initialProps);
	    
	    //Deploy a container in KIE Server
	    KieServicesConfiguration config = KieServicesFactory.newJMSConfiguration(
	    		context, USER, PASSWORD);
		config.addJaxbClasses(extraJaxbClasses);
		KieServicesClient client = KieServicesFactory.newKieServicesClient(config);
	    KieContainerResource kContainer = new KieContainerResource();
	    ReleaseId releaseId = new ReleaseId();
	    releaseId.setGroupId("org.drools.devguide");
	    releaseId.setArtifactId("chapter-11-kjar");
	    releaseId.setVersion("0.1-SNAPSHOT");
	    kContainer.setReleaseId(releaseId);
	    kContainer.setContainerId("my-deploy");
	    client.createContainer("my-deploy", kContainer);
	   
	    //Do an insert of an object and a fireAllRules for that deployment
	    RuleServicesClient ruleClient = client.getServicesClient(RuleServicesClient.class);
	    List<Command<?>> commands = new ArrayList<Command<?>>(2);
	    commands.add(CommandFactory.newInsert(new JaxbItem(199.0, Item.Category.NA), "item-out-id"));
	    commands.add(CommandFactory.newFireAllRules());
	    BatchExecutionCommand batch = CommandFactory.newBatchExecution(commands);
	    ServiceResponse<String> response = ruleClient.executeCommands("my-deploy", batch);
	    Assert.assertNotNull(response);
	    JaxbSerializationProvider provider = ClientJaxbSerializationProvider.newInstance(
	    		Arrays.asList(JaxbItem.class, ExecutionResultImpl.class));
	    ExecutionResults results = (ExecutionResults) provider.deserialize(response.getResult());
	    Assert.assertNotNull(results);
	    Assert.assertNotNull(results.getValue("item-out-id"));
	    Object retval = results.getValue("item-out-id");
	    Assert.assertNotNull(retval);
	    Assert.assertTrue(retval instanceof Item);
	    Item i = (Item) retval;
	    Assert.assertEquals(Item.Category.LOW_RANGE, i.getCategory());
	}
}
