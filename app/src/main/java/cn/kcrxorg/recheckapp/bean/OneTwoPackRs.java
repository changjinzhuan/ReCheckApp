package cn.kcrxorg.recheckapp.bean;

import java.util.Date;

public class OneTwoPackRs {
	private String updatetime;
	 private String onepackcode;

	    private String twopackcode;
	private String bankname="";
	private String operatusername="";
	private String checkusername="";
	private String cashtypename="";
	private String cashvouchername="";
	
	public String getOnepackcode() {
		return onepackcode;
	}
	public void setOnepackcode(String onepackcode) {
		this.onepackcode = onepackcode;
	}
	public String getTwopackcode() {
		return twopackcode;
	}
	public void setTwopackcode(String twopackcode) {
		this.twopackcode = twopackcode;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getOperatusername() {
		return operatusername;
	}
	public void setOperatusername(String operatusername) {
		this.operatusername = operatusername;
	}
	public String getCheckusername() {
		return checkusername;
	}
	public void setCheckusername(String checkusername) {
		this.checkusername = checkusername;
	}
	public String getCashtypename() {
		return cashtypename;
	}
	public void setCashtypename(String cashtypename) {
		this.cashtypename = cashtypename;
	}
	public String getCashvouchername() {
		return cashvouchername;
	}
	public void setCashvouchername(String cashvouchername) {
		this.cashvouchername = cashvouchername;
	}
	
}
