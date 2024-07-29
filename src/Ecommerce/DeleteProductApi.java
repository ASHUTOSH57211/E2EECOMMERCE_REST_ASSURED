package Ecommerce;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

import org.testng.Assert;

public class DeleteProductApi {
	
	public static void deleteProduct(String token,String productId) {
		RequestSpecification deleteProdBaseReq=	new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).setContentType(ContentType.JSON)
				.build();

				RequestSpecification deleteProdReq =given().log().all().spec(deleteProdBaseReq).pathParam("productId",productId);

				String deleteProductResponse = deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().
				extract().response().asString();

				JsonPath js1 = new JsonPath(deleteProductResponse);

				Assert.assertEquals("Product Deleted Successfully",js1.get("message"));
	}

}
