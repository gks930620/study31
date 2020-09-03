package com.study.common.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ProdVO {
	private String prodId;             
	private String prodName;           
	private String prodLgu;            
	private int prodPrice;                  
	private String prodDetail;         
	private String prodImg;
	private String prodRegDate;
	
	public ProdVO() {}
	
	public ProdVO(String prodId, String prodName, String prodLgu, int prodPrice, String prodDetail, String prodImg, String prodRegDate
			) {
		super();
		this.prodId = prodId;
		this.prodName = prodName;
		this.prodLgu = prodLgu;
		this.prodPrice = prodPrice;
		this.prodDetail = prodDetail;
		this.prodImg = prodImg;
		this.prodRegDate = prodRegDate;
	}
	// toString을 편하게 해주는 ToStringBuilder 로 변경할 것
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}            
	
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getProdLgu() {
		return prodLgu;
	}
	public void setProdLgu(String prodLgu) {
		this.prodLgu = prodLgu;
	}
	public int getProdPrice() {
		return prodPrice;
	}
	public void setProdPrice(int prodPrice) {
		this.prodPrice = prodPrice;
	}
	
	public String getProdDetail() {
		return prodDetail;
	}
	public void setProdDetail(String prodDetail) {
		this.prodDetail = prodDetail;
	}
	public String getProdImg() {
		return prodImg;
	}
	public void setProdImg(String prodImg) {
		this.prodImg = prodImg;
	}

	public String getProdRegDate() {
		return prodRegDate;
	}

	public void setProdRegDate(String prodRegDate) {
		this.prodRegDate = prodRegDate;
	}

	
} // class 

