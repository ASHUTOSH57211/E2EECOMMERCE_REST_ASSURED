package Ecommerce;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pojoclasses.LoginPageRequestPojo;
import pojoclasses.LoginPageResponsePojo;
import utilities.ProjectConfigurations;

import static io.restassured.RestAssured.*;

import java.io.IOException;

import org.testng.Assert;
//import org.testng.annotations.Test;


public class loginApi {
	public static String[] loginUsingAPI() throws IOException {

		//initialising the base uri and content type for login
		RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
				setContentType(ContentType.JSON).build();
		
		//getting userid and password from config.properties and passing through the pojo class to create request body
		String id = ProjectConfigurations.LoadProperties("loginId");
		String password = ProjectConfigurations.LoadProperties("password");
		System.out.println("id is "+id + "password is "+password );
		
		LoginPageRequestPojo lpp = new LoginPageRequestPojo();
		lpp.setUserEmail(id);
		lpp.setUserPassword(password);
		
		//spec builder using the base initialistion for given().Here body object is passed as body
		RequestSpecification reqLogin = given().relaxedHTTPSValidation().log().all().spec(requestSpec).body(lpp);
		
		
		//Getting the response and passing it throughpojo class (deserilization)
		LoginPageResponsePojo Lpro = reqLogin.when().post("/api/ecom/auth/login").then().log().all().extract().response()
				.as(LoginPageResponsePojo.class);
		
		String token = Lpro.getToken();
		String userID = Lpro.getUserId();
		String message = Lpro.getMessage();
		
		Assert.assertEquals(message, "Login Successfully");
		
		System.out.println("token is "+ token );
		System.out.println("userid is "+ userID );
		System.out.println("message is "+ message );
		String [] authtoken = new String[2];
		authtoken[0] = token;
		authtoken[1] = userID;
		return authtoken;
		
		
	}

}
