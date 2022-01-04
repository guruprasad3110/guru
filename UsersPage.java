package restapitests;



import org.testng.annotations.Test;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.com.base.TestBase;
import org.testng.Assert;


/** Test Cases to perform get and post actions in a page
 * 
 * @author GBMoorthy
 *
 */

public class UsersPage extends TestBase{

	
	@Test
	
	public void  VerifyTotalPagesandStatusCode() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException 
	{
	 
		//Get total Pages from the JSON Response and compare with the Expected Total pages in Excel
		
	  Assert.assertEquals(GetActualResult("/total").toString(), utils.GetExpectedValue("total"),"Values of total Pages is:");
	  
	
	}
	
	@Test
	
	public void  VerifyStatusMessage() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException
	{
		//Get Status Code of a Get Call  and compare with the Expected Status Code in Excel
		
		Assert.assertEquals(GetActualResult().toString(), utils.GetExpectedValue("Status Message"),"Status Message :");
		
		
	}
	
	@Test
	public void getUserID() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException 
	{
		
		//Get Id of the Second User from the JSON Response and compare with the Expected Value in Excel
		
		Assert.assertEquals(GetActualResult("/data[1]/id").toString(), utils.GetExpectedValue("id"),"Id Value from Response :");
		  
		 
	}
	
	@Test
	public void  createuser() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException 
	{
		
		//Post call to create user with name and Job field mentioned in Excel 
		
		postValues();
		
		//Get Status Code to verify user is created successfully
		
		Assert.assertEquals(GetActualResult().toString(), utils.GetExpectedValue("Status Code"),"Status Code after Post Call :");
		
		  
		 
	}
	
}
