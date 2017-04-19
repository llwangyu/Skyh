package android.com.skyh.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class HyList {
	@JsonProperty(value = "dydhbList")
	private List<RsEntityResult> dydhbList;
	@JsonProperty(value = "dxzhyList")
	private List<RsEntityResult> dxzhyList;
	@JsonProperty(value = "dkxxjlList")
	private List<RsEntityResult> dkxxljList;
	@JsonProperty(value = "dwhytList")
	private List<RsEntityResult> dzwhtList;
	@JsonProperty(value = "zbwyhList")
	private List<RsEntityResult> zbwyhList;

	public List<RsEntityResult> getZbwyhList() {
		return zbwyhList;
	}

	public void setZbwyhList(List<RsEntityResult> zbwyhList) {
		this.zbwyhList = zbwyhList;
	}

	public List<RsEntityResult> getDydhbList() {
		return dydhbList;
	}

	public void setDydhbList(List<RsEntityResult> dydhbList) {
		this.dydhbList = dydhbList;
	}

	public List<RsEntityResult> getDxzhyList() {
		return dxzhyList;
	}

	public void setDxzhyList(List<RsEntityResult> dxzhyList) {
		this.dxzhyList = dxzhyList;
	}

	public List<RsEntityResult> getDkxxljList() {
		return dkxxljList;
	}

	public void setDkxxljList(List<RsEntityResult> dkxxljList) {
		this.dkxxljList = dkxxljList;
	}

	public List<RsEntityResult> getDzwhtList() {
		return dzwhtList;
	}

	public void setDzwhtList(List<RsEntityResult> dzwhtList) {
		this.dzwhtList = dzwhtList;
	}
}
