package com.ffam.workdistapi;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FfamWorkDistributionApiApplicationTests {
	
	@Test
	public void testNewInstance() {
		assertNotNull(new FfamWorkDistributionApiApplication());
	}
}
