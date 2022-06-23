package Logic;

public class Order {
	
	private String orderid,parkname,date,time,nooftravelers,status,total;
	
	public Order(String orderid,String parkname,String date,String time,String nooftravelers,String status,String total){

		this.orderid=orderid;
		this.parkname = parkname;
		this.date = date;
		this.time = time;
		this.nooftravelers=nooftravelers;
		this.status = status;
		this.total=total;
	
}

	public String getNooftravelers() {
		return nooftravelers;
	}

	public void setNooftravelers(String nooftravelers) {
		this.nooftravelers = nooftravelers;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getParkname() {
		return parkname;
	}

	public void setParkname(String parkname) {
		this.parkname = parkname;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}



}
