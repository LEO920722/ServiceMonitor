package com.yisquare.bean;

public class ScheBatchBean {
	private String SCHE_ID;
	private String MAIL_SUBJECT;
	private String ACTIVE_FLAG;
	private String LAST_RUN_TIMESTAMP;
	private String MAIL_ROUTER_ID;
	private String RULE_ID;
	
	public String getScheID() {
		return SCHE_ID;
	}

	public void setScheID(String ScheID) {
		this.SCHE_ID = ScheID;
	}

	public String getMailSubject() {
		return MAIL_SUBJECT;
	}

	public void setMailSubject(String MailSubject) {
		MAIL_SUBJECT = MailSubject;
	}
	
	public String getActiveFlag() {
		return ACTIVE_FLAG;
	}

	public void setActiveFlag(String ActiveFlag) {
		ACTIVE_FLAG = ActiveFlag;
	}
	
	public String getLastRunTimestamp() {
		return LAST_RUN_TIMESTAMP;
	}

	public void setLastRunTimestamp(String LastRunTimestamp) {
		LAST_RUN_TIMESTAMP = LastRunTimestamp;
	}

	public String getMailRouterID() {
		return MAIL_ROUTER_ID;
	}

	public void seMailRouterID(String MailRouterID) {
		MAIL_ROUTER_ID = MailRouterID;
	}
	
	public String getRuleID() {
		return RULE_ID;
	}

	public void setRuleID(String RuleID) {
		RULE_ID = RuleID;
	}
}
