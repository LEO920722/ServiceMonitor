package com.yisquare.bean;

public class RuleBean {
	private String SCHE_ID;
	private String RULE_ID;
	private String RULE_NAME;
	private String INTERFACE_NAME;
	private String SERVICE_NAME;
	private String CONTENT_LOGGING;
	private String ACTIVE_FLAG;

	public String getScheID() {
		return SCHE_ID;
	}

	public void setScheID(String ScheID) {
		SCHE_ID = ScheID;
	}

	public String getRuleID() {
		return RULE_ID;
	}

	public void setRuleID(String RuleID) {
		RULE_ID = RuleID;
	}

	public String getRuleName() {
		return RULE_NAME;
	}

	public void setRuleName(String RuleName) {
		RULE_NAME = RuleName;
	}

	public String getInterfaceName() {
		return INTERFACE_NAME;
	}

	public void setInterfaceName(String InterfaceName) {
		INTERFACE_NAME = InterfaceName;
	}

	public String getServiceName() {
		return SERVICE_NAME;
	}

	public void setServiceName(String ServiceName) {
		SERVICE_NAME = ServiceName;
	}

	public String getContentLogging() {
		return CONTENT_LOGGING;
	}

	public void setContentLogging(String ContentLogging) {
		CONTENT_LOGGING = ContentLogging;
	}

	public String getActiveFlag() {
		return ACTIVE_FLAG;
	}

	public void setActiveFlag(String ActiveFlag) {
		ACTIVE_FLAG = ActiveFlag;
	}

}
