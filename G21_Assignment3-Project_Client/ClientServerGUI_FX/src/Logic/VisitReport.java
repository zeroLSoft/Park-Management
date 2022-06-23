package Logic;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class VisitReport {
	String DayOfMonth ,Single,SingleWithSub,visitorsInGroups,Groups,Subscription,Subscribers,Total;
	
	public VisitReport(String DayOfMonth,String Single,String SingleWithSub,String Groups,String visitorsInGroups,String Subscription,String Subscribers,String Total)
	{
		this.DayOfMonth=DayOfMonth;
		this.Single=Single;
		this.SingleWithSub = SingleWithSub;
		this.Groups = Groups ;
		this.visitorsInGroups = visitorsInGroups;
		this.Subscription = Subscription ;
		this.Subscribers = Subscribers ;
		this.Total = Total ;
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


	public String getTotal() {
		return Total;
	}


	public void setTotal(String total) {
		Total = total;
	}



}
