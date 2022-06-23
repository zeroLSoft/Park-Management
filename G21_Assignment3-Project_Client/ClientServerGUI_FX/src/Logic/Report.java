package Logic;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class Report {
	String DayOfMonth ,Single,SingleWithSub,visitorsInGroups,Groups,Subscription,Subscribers,Usege,Profit;
	
	
	public Report(String DayOfMonth,String Single,String SingleWithSub,String visitorsInGroups,String Groups,String Subscription,String Subscribers,String Usege,String Profit)
	{
		this.DayOfMonth=DayOfMonth;
		this.Single=Single;
		this.SingleWithSub = SingleWithSub;
		this.visitorsInGroups = visitorsInGroups;
		this.Groups = Groups ;
		this.Subscription = Subscription ;
		this.Subscribers = Subscribers ;
		this.Usege = Usege ;
		this.Profit = Profit ;
	}


	public String getDayOfMonth() {
		return DayOfMonth;
	}


	public void setDayOfMonth(String dayOfMonth) {
		DayOfMonth = dayOfMonth;
	}


	public String getSingle() {
		return Single;
	}


	public void setSingle(String single) {
		Single = single;
	}


	public String getSingleWithSub() {
		return SingleWithSub;
	}


	public void setSingleWithSub(String singleWithSub) {
		SingleWithSub = singleWithSub;
	}


	public String getVisitorsInGroups() {
		return visitorsInGroups;
	}


	public void setVisitorsInGroups(String visitorsInGroups) {
		this.visitorsInGroups = visitorsInGroups;
	}


	public String getGroups() {
		return Groups;
	}


	public void setGroups(String groups) {
		Groups = groups;
	}


	public String getSubscription() {
		return Subscription;
	}


	public void setSubscription(String subscription) {
		Subscription = subscription;
	}


	public String getSubscribers() {
		return Subscribers;
	}


	public void setSubscribers(String subscribers) {
		Subscribers = subscribers;
	}


	public String getUsege() {
		return Usege;
	}


	public void setUsege(String usege) {
		Usege = usege;
	}


	public String getProfit() {
		return Profit;
	}


	public void setProfit(String profit) {
		Profit = profit;
	}



	
}
