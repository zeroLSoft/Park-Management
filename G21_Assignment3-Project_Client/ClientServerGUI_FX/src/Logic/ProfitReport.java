package Logic;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class ProfitReport {
	String Day ,Single,SingleProfit,Group,GroupProfit,Subscriptions,SubscriptionsProfit,TotakProfit;
	
	public ProfitReport(String Day,String Single,String SingleProfit,String Group,String GroupProfit,String Subscriptions,String SubscriptionsProfit,String TotakProfit)
	{
		this.Day=Day;
		this.Single=Single;
		this.SingleProfit = SingleProfit;
		this.Group = Group ;
		this.GroupProfit = GroupProfit;
		this.Subscriptions = Subscriptions;
		this.SubscriptionsProfit = SubscriptionsProfit;
		this.TotakProfit = TotakProfit;
	}

	public String getDay() {
		return Day;
	}

	public void setDay(String day) {
		Day = day;
	}

	public String getSingle() {
		return Single;
	}

	public void setSingle(String single) {
		Single = single;
	}

	public String getSingleProfit() {
		return SingleProfit;
	}

	public void setSingleProfit(String singleProfit) {
		SingleProfit = singleProfit;
	}

	public String getGroup() {
		return Group;
	}

	public void setGroup(String group) {
		Group = group;
	}

	public String getGroupProfit() {
		return GroupProfit;
	}

	public void setGroupProfit(String groupProfit) {
		GroupProfit = groupProfit;
	}

	public String getSubscriptions() {
		return Subscriptions;
	}

	public void setSubscriptions(String subscriptions) {
		Subscriptions = subscriptions;
	}

	public String getSubscriptionsProfit() {
		return SubscriptionsProfit;
	}

	public void setSubscriptionsProfit(String subscriptionsProfit) {
		SubscriptionsProfit = subscriptionsProfit;
	}

	public String getTotakProfit() {
		return TotakProfit;
	}

	public void setTotakProfit(String totakProfit) {
		TotakProfit = totakProfit;
	}
	
	
	
}
