package pojoclasses;

import java.util.List;

public class AddProductRequestPojo {
	private List<AddOrdersDetailsPojo> orders;

	public List<AddOrdersDetailsPojo> getOrders() {
		return orders;
	}

	public void setOrders(List<AddOrdersDetailsPojo> orders) {
		this.orders = orders;
	}

}
