package hdispatch.core.dispatch.azkaban.entity.schedule;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" }) 
public class Schedule {
	 private JSONObject obj;
	  public Schedule() {
	    }
	    public Schedule(JSONObject obj) {
	        this.obj = obj;
	    }
	public JSONObject getObj1() {
		return obj;
	}
	public void setObj(JSONObject obj) {
		this.obj = obj;
	}
	    public boolean IsNotNull()
	    {
	    	return obj.has("schedule");
	    }
	    public JSONObject getSchdule()
	    {
	    	return obj.getJSONObject("schedule");
	    }
	    public String getNextExecTime()
	    {
	    	return obj.getString("nextExecTime");
	    }
	    public String getPeriod()
	    {
	    	return obj.getString("period");
	    }
	    public String getSubmitUser()
	    {
	    	return obj.getString("submitUser");
	    }
	    public JSONObject getExecutionOptions()
	    {
	    	return obj.getJSONObject("executionOptions");
	    }
	    public String getScheduleId()
	    {
	    	return obj.getString("scheduleId");
	    }
	    public String getFirstSchedTime()
	    {
	    	return obj.getString("firstSchedTime");
	    }

	    
	    public boolean getNotifyOnFirstFailure()
	    {
	    	return obj.getBoolean("notifyOnFirstFailure");
	    }
	    public boolean getNotifyOnLastFailure()
	    {
	    	return obj.getBoolean("notifyOnLastFailure");
	    }
	    public JSONArray getFailureEmails()
	    {
	    	return obj.getJSONArray("failureEmails");
	    }
	    public JSONArray getSuccessEmails()
	    {
	    	if(obj.isNull("successEmails"))
	    	return new JSONArray();
	    	else
	    	return obj.getJSONArray("successEmails");
	    }
	    public Integer getPipelineLevel()
	    {
	    	return obj.getInt("pipelineLevel");
	    }
	    public Integer getQueueLevel()
	    {
	    	return obj.getInt("queueLevel");
	    }
	    public String getConcurrentOption()
	    {
	    	return obj.getString("concurrentOption");
	    }
	    public String getMailCreator()
	    {
	    	return obj.getString("mailCreator");
	    }
	    public boolean getMemoryCheck()
	    {
	    	return obj.getBoolean("memoryCheck");
	    }
	    public JSONObject getFlowParameters()
	    {
	    	if(obj.isNull("flowParameters"))
	    		return new JSONObject();
	    	else
	    	return obj.getJSONObject("flowParameters");
	    }
	    public String getFailureAction()
	    {
	    	return obj.getString("failureAction");
	    }
	    public JSONArray getDisabledJobs()
	    {
	    	return obj.getJSONArray("disabledJobs");
	    }
	    public Integer getPipelineExecutionId()
	    {
	    	return obj.getInt("pipelineExecutionId");
	    }
	    public boolean getFailureEmailsOverridden()
	    {
	    	return obj.getBoolean("failureEmailsOverridden");
	    }
	    public boolean getSuccessEmailsOverridden()
	    {
	    	return obj.getBoolean("successEmailsOverridden");
	    }
	    
}
