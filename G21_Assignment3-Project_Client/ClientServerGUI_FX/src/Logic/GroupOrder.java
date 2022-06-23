package Logic;

public class GroupOrder {
	String InstroctureID,GroupID,NumberOfVisitors,DateOfVisit, cost;
	
	public GroupOrder(String InstroctureID,String GroupID,String NumberOfVisitors,String DateOfVisit,String cost)
	{
		this.InstroctureID=InstroctureID;
		this.GroupID = GroupID;
		this.NumberOfVisitors = NumberOfVisitors;
		this.DateOfVisit = DateOfVisit;
		this.cost = cost;
	}

	public String getInstroctureID() {
		return InstroctureID;
	}

	public void setInstroctureID(String instroctureID) {
		InstroctureID = instroctureID;
	}

	public String getGroupID() {
		return GroupID;
	}

	public void setGroupID(String groupID) {
		GroupID = groupID;
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

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}
}
