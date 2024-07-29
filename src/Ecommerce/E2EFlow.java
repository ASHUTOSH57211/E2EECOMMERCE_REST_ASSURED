package Ecommerce;

import java.io.IOException;

public class E2EFlow {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		//login to ecomm portal using API 
		String [] tokenDetails = loginApi.loginUsingAPI();
		
		System.out.println(tokenDetails[0]);
		String Token = tokenDetails[0];
		String userID = tokenDetails[1];
		
		//Add product
		String productName = "TestDress";
		String productID = AddProductApi.AddProduct(Token, userID,productName);
		
		//placeORder
		String orderID = PlaceOrderApi.placeorder(Token,productID);
		
		/*
		 	UI Validation to check product is added ,successfully placed
			after placed the order is deleted.  
			delete API is called inside to delete the product and then alidated in UI
		*/
		EcommerceUI.logintoEcomUI(productName,orderID,Token,productID);
		
		
		

	}

}
