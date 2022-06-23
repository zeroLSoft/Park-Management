package Logic;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class Desition {
	String description ,befor,after,status,park;
	ComboBox combo;
	
	public Desition(String park,String description,String befor,String after,ObservableList status)
	{
		this.park=park;
		this.description=description;
		this.befor = befor;
		this.after = after;
		this.combo = new ComboBox(status) ;
	}
	

	public String getPark() {
		return park;
	}


	public void setPark(String park) {
		this.park = park;
	}


	public ComboBox getCombo() {
		return combo;
	}


	public void setCombo(ComboBox combo) {
		this.combo = combo;
	}


	public Desition(String description,String befor,String after,String status)
	{
		this.description=description;
		this.befor = befor;
		this.after = after;
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBefor() {
		return befor;
	}

	public void setBefor(String befor) {
		this.befor = befor;
	}

	public String getAfter() {
		return after;
	}

	public void setAfter(String after) {
		this.after = after;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
