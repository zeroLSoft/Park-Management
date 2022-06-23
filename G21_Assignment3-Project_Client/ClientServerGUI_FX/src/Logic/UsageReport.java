package Logic;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class UsageReport {
	String Index ,DayNum,TotalVisitor,Capacity,usage;
	
	public UsageReport(String Index,String DayNum,String TotalVisitor,String Capacity,String usage)
	{
		this.Index=Index;
		this.DayNum=DayNum;
		this.TotalVisitor = TotalVisitor;
		this.Capacity = Capacity ;
		this.usage = usage;
	}
	
	
	public String getIndex() {
		return Index;
	}

	public void setIndex(String index) {
		Index = index;
	}

	public String getDayNum() {
		return DayNum;
	}

	public void setDayNum(String dayNum) {
		DayNum = dayNum;
	}

	public String getTotalVisitor() {
		return TotalVisitor;
	}

	public void setTotalVisitor(String totalVisitor) {
		TotalVisitor = totalVisitor;
	}

	public String getCapacity() {
		return Capacity;
	}

	public void setCapacity(String capacity) {
		Capacity = capacity;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	
}
