package Ecommerce;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojoclasses.AddOrdersDetailsPojo;
import pojoclasses.AddProductRequestPojo;


public class PlaceOrderApi {
	
	public static String placeorder(String Token,String productID) {
		
		RequestSpecification placeOrderRequest =  new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", Token).setContentType(ContentType.JSON).build();
		/*
		 * the request body is a bit complex payload
		 * 	Structure of the body 
		  {
    		"orders": [
        		{
            		"country": "India",
            		"productOrderedId": "66966dc7ae2afd4c0b27426d"
        		}
    			]
		 }
		 */
		
		/*
		 * the inner json pojo is created first with country and prodOrderId and then the object is passed to outer order as a list
		 */
		AddOrdersDetailsPojo aodp = new AddOrdersDetailsPojo();
		aodp.setCountry("India");
		aodp.setProductOrderedId(productID);
		
		AddProductRequestPojo aprp = new AddProductRequestPojo();
		List<AddOrdersDetailsPojo> requestbody = new ArrayList<AddOrdersDetailsPojo>();
		requestbody.add(aodp);
		aprp.setOrders(requestbody);
		
		
		RequestSpecification reqPlaceOrder= given().relaxedHTTPSValidation().log().all().spec(placeOrderRequest).body(aprp);
		
		String responsePlaceOrder = reqPlaceOrder.when().post("/api/ecom/order/create-order")
		.then().log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(responsePlaceOrder);
		String message = js.get("message");
		String responseProdId = js.getString("productOrderId[0]");
		String responseorderId = js.getString("orders[0]");
		System.out.println("*******"+responseProdId);
		
		Assert.assertEquals(message, "Order Placed Successfully");
		Assert.assertEquals(responseProdId, productID);
		return responseorderId;
		
	}

}
