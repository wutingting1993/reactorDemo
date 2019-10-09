package hotel;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hotel {
	private String id;
	private String pid;
	private String pname;
	private String zone;
	private String zoneId;
	private String rname;
	private Date createTime;
	private Map price;
	private String people;
	private String commentScore;
}
