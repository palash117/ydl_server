package dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TempResponse extends Response {

	String str;
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public TempResponse(){};
}
