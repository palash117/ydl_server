package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ResponseList{

	
	private List<Response> rsl;

	public List<Response> getRsl() {
		return rsl;
	}
	public void setRsl(List<Response> rsl) {
		this.rsl = rsl;
	}
	public ResponseList() {
	}
	public ResponseList(Response rs){
		rsl = new ArrayList<>();
		rsl.add(rs);
	}
	
	public void addResponse(Response response){
		rsl.add(response);
	}
}
