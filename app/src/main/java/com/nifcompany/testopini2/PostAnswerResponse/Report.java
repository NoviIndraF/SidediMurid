package com.nifcompany.testopini2.PostAnswerResponse;

import com.google.gson.annotations.SerializedName;

public class Report{

	@SerializedName("Eksklusif")
	private String eksklusif;

	@SerializedName("Intoleran")
	private String intoleran;

	@SerializedName("Kekerasan")
	private String kekerasan;

	@SerializedName("Ekstream")
	private String ekstream;

	public void setEksklusif(String eksklusif){
		this.eksklusif = eksklusif;
	}

	public String getEksklusif(){
		return eksklusif;
	}

	public void setIntoleran(String intoleran){
		this.intoleran = intoleran;
	}

	public String getIntoleran(){
		return intoleran;
	}

	public void setKekerasan(String kekerasan){
		this.kekerasan = kekerasan;
	}

	public String getKekerasan(){
		return kekerasan;
	}

	public void setEkstream(String ekstream){
		this.ekstream = ekstream;
	}

	public String getEkstream(){
		return ekstream;
	}

	@Override
 	public String toString(){
		return 
			"Report{" + 
			"eksklusif = '" + eksklusif + '\'' + 
			",intoleran = '" + intoleran + '\'' + 
			",kekerasan = '" + kekerasan + '\'' + 
			",ekstream = '" + ekstream + '\'' + 
			"}";
		}
}