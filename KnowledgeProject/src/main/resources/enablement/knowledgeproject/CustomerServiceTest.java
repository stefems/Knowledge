package com.rhc.customer;

import java.io.File;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieServices;

import com.rhc.aggregates.Customer;
import com.rhc.entities.Address;
import com.rhc.services.AbstractBusinessServicesTest;

public class CustomerServiceTest extends AbstractBusinessServicesTest{
	
	@BeforeClass
	public static void init(){
		// ensure the latest version of the KieJar is on the classpath
		KieServices.Factory.get().newKieBuilder( new File(getKieJarPath()) ).buildAll();
	}
	
	@Test
	public void shouldSuccessfullyStartACustomerOnboardProcessAndCompleteHumanTaskAndCompleteProcess (){
		// given
		Assert.assertNotNull(customerService);
		
		Customer leia = new Customer();
		leia.setFirstName("Leia");
		leia.setLastName("Organa");
		
		
		// when I start the process
		Long processId = customerService.startCustomerOnboardProcess(leia);
		
		// when I complete the task
		Address leiaAddress = new Address();

		leiaAddress.setNum(15);
		leiaAddress.setStreet("Wow Street");
		leiaAddress.setCity("Raleigh");
		leiaAddress.setState("NC");
		leiaAddress.setZip(115555);
		
		customerService.addAddress(leiaAddress, processId);
		 
		// then the process should complete
		Assert.assertTrue( customerService.isProcessComplete(processId));
	}
	
	
	public static String getKieJarPath(){
		String dir = System.getProperty("user.dir");
		
		String dir2 = dir.substring(0, dir.lastIndexOf("/")) + "/bpmsuite-monolith-knowledge";
		System.err.println( dir2 );
		return dir2;
	}
}
