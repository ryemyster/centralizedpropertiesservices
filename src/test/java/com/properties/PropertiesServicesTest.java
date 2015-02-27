package com.properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.properties.database.DatabaseService;
import com.properties.queueservices.QueueServices;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/application-config.xml" })
public class PropertiesServicesTest {

	private static final Logger logger = LoggerFactory
			.getLogger(PropertiesServicesTest.class);

	@Autowired
	private QueueServices queueServices;

	@Autowired
	private DatabaseService databaseService;

	@Test
	public void test() {
		logger.info("Testing!");
		
		// try {
		// queueServices.generateMessage("Testing This Out!!!");
		// } catch (PropertiesException e) {
		// e.printStackTrace();
		// }

		// databaseService.getProperties("interfaceId");

	}

}
