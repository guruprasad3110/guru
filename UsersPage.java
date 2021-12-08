package restapitests;



import org.testng.annotations.Test;



import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


import org.com.base.TestBase;
import org.testng.Assert;




public class UsersPage extends TestBase{

	
	@Test
	
	public void  userget() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException 
	{
		
		//System.out.println(Initialise().toString());
		Assert.assertEquals(GetActualResult().toString(), utils.GetExpectedValue("Status Code"),"Values equal");
		
		
		 
	}
	@Test
	
	public void  userget1() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException
	{
		Assert.assertEquals(GetActualResult().toString(), utils.GetExpectedValue("Status Code"),"Values equal");
		
		 Assert.assertEquals(GetActualResult(),utils.GetExpectedValue("Response id"),"Values equal");
		 
		 
	}
	
	@Test
	public void  userget3() throws IOException 
	{
		
		  Assert.assertEquals(name,utils.GetExpectedValue("Status Code"),"Status Code Verified");
		  
		 
	}
	
}
