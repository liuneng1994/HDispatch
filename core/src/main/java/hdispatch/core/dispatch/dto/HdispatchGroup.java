package hdispatch.core.dispatch.dto;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by 邓志�??? on 2016/9/7.
 */

@Table(name = "HDISPATCH_GROUP")
public class  HdispatchGroup{
	
	private Long id;

	private String group_name;

	private Long layer_id;

	private String schedule_expression;

	private String flow_id;

	private Byte active;

	private Date create_at;

	private Date update_at;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name == null ? null : group_name.trim();
	}

	public Long getLayer_id() {
		return layer_id;
	}

	public void setLayer_id(Long layer_id) {
		this.layer_id = layer_id;
	}

	public String getSchedule_expression() {
		return schedule_expression;
	}

	public void setSchedule_expression(String schedule_expression) {
		this.schedule_expression = schedule_expression == null ? null : schedule_expression.trim();
	}

	public String getFlow_id() {
		return flow_id;
	}

	public void setFlow_id(String flow_id) {
		this.flow_id = flow_id == null ? null : flow_id.trim();
	}

	public Byte getActive() {
		return active;
	}

	public void setActive(Byte active) {
		this.active = active;
	}

	public Date getCreate_at() {
		return create_at;
	}

	public void setCreate_at(Date create_at) {
		this.create_at = create_at;
	}

	public Date getUpdate_at() {
		return update_at;
	}

	public void setUpdate_at(Date update_at) {
		this.update_at = update_at;
	}

	
}

