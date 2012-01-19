package com.pos.bean;

import java.sql.Date;
import java.sql.Timestamp;

public class Product {
	private int id, qty, createdBy, updatedBy, repoId, storId, shipId, customerId;
	private String _package, description, lotNo, serialNo,  wd, iv, inputNo, inPo, inLot, outputNo, customerOrderNo;
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getOutputNo() {
		return outputNo;
	}
	public void setOutputNo(String outputNo) {
		this.outputNo = outputNo;
	}
	public String getCustomerOrderNo() {
		return customerOrderNo;
	}
	public void setCustomerOrderNo(String customerOrderNo) {
		this.customerOrderNo = customerOrderNo;
	}
	public Date getOutputDate() {
		return outputDate;
	}
	public void setOutputDate(Date outputDate) {
		this.outputDate = outputDate;
	}
	private float ivMin, ivAvg, ivMax, dMin, dAvg, dMax, vfMin, vfAvg, vfMax;
	private boolean isShipped;
	private Timestamp created_at,updated_at;
	private Date inputDate, outputDate;
	public int getRepoId() {
		return repoId;
	}
	public void setRepoId(int repoId) {
		this.repoId = repoId;
	}
	public int getStorId() {
		return storId;
	}
	public void setStorId(int storId) {
		this.storId = storId;
	}
	public int getShipId() {
		return shipId;
	}
	public void setShipId(int shipId) {
		this.shipId = shipId;
	}
	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public String getInputNo() {
		return inputNo;
	}
	public void setInputNo(String inputNo) {
		this.inputNo = inputNo;
	}
	public String getInPo() {
		return inPo;
	}
	public void setInPo(String inPo) {
		this.inPo = inPo;
	}
	public String getInLot() {
		return inLot;
	}
	public void setInLot(String inLot) {
		this.inLot = inLot;
	}
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getPackage() {
		return _package;
	}
	public void setPackage(String _package) {
		this._package = _package;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getWd() {
		return wd;
	}
	public void setWd(String wd) {
		this.wd = wd;
	}
	public String getIv() {
		return iv;
	}
	public void setIv(String iv) {
		this.iv = iv;
	}
	public float getIvMin() {
		return ivMin;
	}
	public void setIvMin(float ivMin) {
		this.ivMin = ivMin;
	}
	public float getIvAvg() {
		return ivAvg;
	}
	public void setIvAvg(float ivAvg) {
		this.ivAvg = ivAvg;
	}
	public float getIvMax() {
		return ivMax;
	}
	public void setIvMax(float ivMax) {
		this.ivMax = ivMax;
	}
	public float getdMin() {
		return dMin;
	}
	public void setdMin(float dMin) {
		this.dMin = dMin;
	}
	public float getdAvg() {
		return dAvg;
	}
	public void setdAvg(float dAvg) {
		this.dAvg = dAvg;
	}
	public float getdMax() {
		return dMax;
	}
	public void setdMax(float dMax) {
		this.dMax = dMax;
	}
	public float getVfMin() {
		return vfMin;
	}
	public void setVfMin(float vfMin) {
		this.vfMin = vfMin;
	}
	public float getVfAvg() {
		return vfAvg;
	}
	public void setVfAvg(float vfAvg) {
		this.vfAvg = vfAvg;
	}
	public float getVfMax() {
		return vfMax;
	}
	public void setVfMax(float vfMax) {
		this.vfMax = vfMax;
	}
	public boolean isShipped() {
		return isShipped;
	}
	public void setShipped(boolean isShipped) {
		this.isShipped = isShipped;
	}
}
