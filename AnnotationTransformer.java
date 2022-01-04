package org.com.base;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

/** Implements the Interface IAnnotationTransformer
 *  Passes the list of Test cases to be executed to TestBase class
 * @author GBMoorthy
 *
 */
	public class AnnotationTransformer implements IAnnotationTransformer{

	
	
		
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod){
	
		
		//Comparing Test cases in excel sheet and Testng class to exceute it
		
		for(String tests:TestBase.testnames()) {
			
			if (!(testMethod.getName().equals(tests))){
				
				annotation.setEnabled(false);
			
			}
			else {
				annotation.setEnabled(true);
				break;
			}
		
		}
	 
	
	

	}

}
	
	/*Class<?> classobj = Class.forName("org.com.restclient.RestAPICall");
	  
    // Get Method Object
    Method[] methods = classobj.getMethods();

    // Iterate through methods
   for (Method method : methods) {

        // We are only taking method defined in the demo class
        // We are not taking other methods of the object class
       
            // apply getReturnType() method
            Class<?> returnParam = method.getReturnType();

            // print return Tyoe class object of method Object
            System.out.println("\nMethod Name : "
                               + method.getName());

            System.out.println("Return Type Details: " + returnParam.getName());
            
            if(method.getName().equals(methodcell)) {
          	  
          Object obj=(Object)method.invoke(methodcell, "https://reqres.in//api//users?page=2");
          	  System.out.println("invoked"+obj);
            }
        
    }*/