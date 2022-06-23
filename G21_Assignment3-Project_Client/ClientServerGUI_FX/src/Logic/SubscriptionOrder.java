package Logic;

public class SubscriptionOrder {
		String subscriptionID,NumberOfVisitors,DateOfVisit,OrderID, cost;
		
		public SubscriptionOrder(String subscriptionID,String NumberOfVisitors,String DateOfVisit,String OrderID,String cost)
		{
			this.subscriptionID=subscriptionID;
			this.NumberOfVisitors = NumberOfVisitors;
			this.DateOfVisit = DateOfVisit;
			this.OrderID = OrderID;
			this.cost = cost;
		}

		public String getSubscriptionID() {
			return subscriptionID;
		}

		public void setSubscriptionID(String subscriptionID) {
			this.subscriptionID = subscriptionID;
		}

		public String getNumberOfVisitors() {
			return NumberOfVisitors;
		}

		public void setNumberOfVisitors(String numberOfVisitors) {
			NumberOfVisitors = numberOfVisitors;
		}

		public String getDateOfVisit() {
			return DateOfVisit;
		}

		public void setDateOfVisit(String dateOfVisit) {
			DateOfVisit = dateOfVisit;
		}

		public String getOrderID() {
			return OrderID;
		}

		public void setOrderID(String orderID) {
			OrderID = orderID;
		}

		public String getCost() {
			return cost;
		}

		public void setCost(String cost) {
			this.cost = cost;
		}


		
		
	

}
