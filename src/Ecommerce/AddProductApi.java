package Ecommerce;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AddProductApi {

	
	public static String AddProduct(String Token,String USERID,String productName) throws IOException {
//		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2Njk1NmMxYmFlMmFmZDRjMGIyNjM5NTEiLCJ1c2VyRW1haWwiOiJ0ZXN0YXBpQGV4YW1wbGUuY29tIiwidXNlck1vYmlsZSI6ODc2MzY2NzQzNywidXNlclJvbGUiOiJjdXN0b21lciIsImlhdCI6MTcyMTMyNjQ2MiwiZXhwIjoxNzUyODg0MDYyfQ.YFC608fSXA-lpIDt63rtvaYNfyr88VfzItYySob1wS8";
//		String userId = "66956c1bae2afd4c0b263951";
		RequestSpecification reqAddProductBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", Token).build();
		
		
		RequestSpecification reqAddProduct = given().spec(reqAddProductBase).param("productName", productName)
		.param("productAddedBy", USERID).param("productCategory", "fashion")
		.param("productSubCategory", "shirts").param("productPrice", "11500")
		.param("productDescription", "Lenova").param("productFor", "men")
		.multiPart("productImage",new File(System.getProperty("user.dir")+"//src//img//demo_img.jpeg"));
		
		String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
		.then().log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(addProductResponse);
		String message = js.get("message");
		String productId = js.getString("productId");
		
		System.out.println(message);
		System.out.println(productId);
		Assert.assertEquals("Product Added Successfully", message);
		
		
		return productId;
		
	}
}
