package Server;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import ServerControl.QuaryManagmant;

public class Multi extends Thread {
	private ArrayList<String> parameters = new ArrayList<String>();
	private ArrayList<String> order = new ArrayList<String>();
	private boolean flag;

	/**
	 * thread that modify data in real time
	 * updating orders and chancing status according to time
	 * updating messeges
	 * sending notifications
	 * Handling waitlist
	 */
	public void run() {

		// This thread runs and modifies out dated orders and waiting list.
		while (true) {
			try {
				parameters.clear();
				// Getting all the orders that are not canceled
				String query = "SELECT * FROM gonature.order WHERE status = 'waitinglist' OR status = 'ready' OR status = 'almostpending' OR status = 'pending';";
				parameters.add("UserControl");
				parameters.add("select from table");
				parameters.add(query);

				order = QuaryManagmant.getFromTable(parameters);

				// orders are found

				if (!order.get(0).equals("Not found")) {

					DateTimeFormatter formatter_21 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					String datePlus1 = (java.time.LocalDate.now().plusDays(1)).format(formatter_21);
					String currentDate = (java.time.LocalDate.now()).format(formatter_21);
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					Date date = new Date();
					String[] temp = formatter.format(date).split("\\s+");
					String[] tem2 = temp[1].split(":");
					String currentTime = tem2[0] + ":" + tem2[1];

					for (int i = 0; i < order.size(); i += 19) {
						String order_date, order_time;
						order_date = order.get(i + 2);
						order_time = order.get(i + 3);
						switch (order.get(i + 6)) {
						case "pending":

							// The order was created on the same day of the visit
							if (currentDate.equals(order_date)) {

								parameters.clear();
								query = "UPDATE gonature.order SET status = 'ready' , readytime = ? WHERE orderID = ?;";
								parameters.add("UserControl");
								parameters.add("set to table");
								parameters.add(query);
								parameters.add(currentTime);
								parameters.add(order.get(i));
								QuaryManagmant.setToTable(parameters);

							}

							else if (datePlus1.equals(order_date)) {
								// The order is tomorrow but there are less than 24 hours to the order
								if (calculateTime(currentTime) >= calculateTime(order_time)) {

									parameters.clear();
									query = "UPDATE gonature.order SET status = 'ready' , readytime = ? WHERE orderID = ?;";
									parameters.add("UserControl");
									parameters.add("set to table");
									parameters.add(query);
									parameters.add(currentTime);
									parameters.add(order.get(i));
									QuaryManagmant.setToTable(parameters);

									// sending notification to the user
								}
							}
							break;

						case "ready":
							manageOrders(i, currentTime, 120);
							break;

						case "almostpending":
							manageOrders(i, currentTime, 60);
							break;

						case "waitinglist":
							if (currentDate.equals(order_date)
									&& calculateTime(order.get(i + 3)) < calculateTime(currentTime)) {
								// Delete out dated order from waiting list
								parameters.clear();
								query = "DELETE FROM `gonature`.`waitinglist` WHERE (`Order_id` = ?);";
								parameters.add("UserControl");
								parameters.add("set to table");
								parameters.add(query);
								parameters.add(order.get(i));
								QuaryManagmant.setToTable(parameters);

								// Delete out dated order with status waiting list
								parameters.clear();
								query = "DELETE FROM gonature.order WHERE orderID = ?;";
								parameters.add("UserControl");
								parameters.add("set to table");
								parameters.add(query);
								parameters.add(order.get(i));
								QuaryManagmant.setToTable(parameters);
							}

							break;

						}

					}
				}

				sleep(1000000);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private int calculateTime(String value) {
		String[] time = value.split(":");
		int hour = Integer.parseInt(time[0]);
		int minutes = Integer.parseInt(time[1]);
		return hour * 60 + minutes;
	}

	private void manageOrders(int i, String currentTime, int timer) {
		String readytime = order.get(i + 18);
		// Order was in ready state for more than 2 hours
		if (calculateTime(currentTime) - calculateTime(readytime) >= timer) {

			parameters.clear();
			String query = "UPDATE gonature.order SET status = 'canceled' WHERE orderID = ?;";
			parameters.add("UserControl");
			parameters.add("set to table");
			parameters.add(query);
			parameters.add(order.get(i));
			QuaryManagmant.setToTable(parameters);

			// Check if there are orders in the waiting list for that time
			int amountOfPeople = Integer.parseInt(order.get(i+4));
			String date1 = order.get(i+2);
			String time = order.get(i+3);
			String park = order.get(i+1);
			query = "SELECT * FROM `gonature`.`waitinglist` WHERE date = ? AND time = ? AND park = ?;";
			parameters.clear();
			parameters.add("UserControl");
			parameters.add("select from table");
			parameters.add(query);
			parameters.add(date1);
			parameters.add(time);
			parameters.add(park);
			ArrayList<String> result1 = QuaryManagmant.getFromTable(parameters);

			// There are orders in waiting list for that time and date
			if (result1.get(0).equals("Not found") != true) {
				while (amountOfPeople > 0 && !result1.get(0).equals("Not found")) {
					for (int j = 0; j < result1.size(); j += 6) {
						// looking for the First in the queue
						if (result1.get(j + 1).equals("1")) {
							// number of people in the current first place waiting list order
							int current = Integer.parseInt(result1.get(j + 5));
							// Enough space for the first order to become ready
							if (amountOfPeople - current >= 0) {
								parameters.clear();
								query = "UPDATE gonature.order SET status=? , readytime=? WHERE orderID=?";
								parameters.add("UserControl");
								parameters.add("set to table");
								parameters.add(query);
								parameters.add("almostpending"); // Setting the time when the order left the waiting
																// list
								parameters.add(currentTime);
								parameters.add(result1.get(j));
								QuaryManagmant.setToTable(parameters);

								// updating the waiting list queue
								for (int k = 0; k < result1.size(); k += 6) {
									int num = Integer.parseInt(result1.get(k + 1)) - 1;
									parameters.clear();
									query = "UPDATE gonature.waitinglist SET Queue = ? WHERE Order_id=?";
									parameters.add("UserControl");
									parameters.add("set to table");
									parameters.add(query);
									parameters.add(String.valueOf(num));
									parameters.add(result1.get(k));
									QuaryManagmant.setToTable(parameters);
								}
								// Deleting order from waiting list
								query = "DELETE FROM `gonature`.`waitinglist` WHERE (`Order_id` = ?);";
								parameters.clear();
								parameters.add("UserControl");
								parameters.add("set to table");
								parameters.add(query);
								parameters.add(result1.get(j));
								QuaryManagmant.setToTable(parameters);

								// updating the amount of people after the new order
								amountOfPeople -= current;
								flag=true;
								
							}
							else return;

						}
						if(flag)
							break;
					}

					// Updating the result after taking order from waiting list
					query = "SELECT * FROM `gonature`.`waitinglist` WHERE date = ? AND time = ? AND park = ?;";
					parameters.clear();
					parameters.add("UserControl");
					parameters.add("select from table");
					parameters.add(query);
					parameters.add(date1);
					parameters.add(time);
					parameters.add(park);
					result1 = QuaryManagmant.getFromTable(parameters);
					flag=false;
				}
			}

		}
	}
}
