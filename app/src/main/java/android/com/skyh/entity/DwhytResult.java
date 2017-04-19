package android.com.skyh.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DwhytResult {
	@JsonProperty(value = "dwhyt")
	private RsEntityResult dwhyt;

	@JsonProperty(value = "dydhb")
	private RsEntityResult dydhb;
		@JsonProperty(value = "dkxxjl")
	private RsEntityResult dkxxjl;
	@JsonProperty(value = "dxzhy")
	private RsEntityResult dxzhy;
	@JsonProperty(value = "zbwyh")
	private RsEntityResult zbwyh;

	public RsEntityResult getDxzhy() {
		return dxzhy;
	}

	public void setDxzhy(RsEntityResult dxzhy) {
		this.dxzhy = dxzhy;
	}

	public RsEntityResult getDkxxjl() {
		return dkxxjl;
	}

	public void setDkxxjl(RsEntityResult dkxxjl) {
		this.dkxxjl = dkxxjl;
	}

	public RsEntityResult getDwhyt() {
		return dwhyt;
	}

	public void setDwhyt(RsEntityResult dwhyt) {
		this.dwhyt = dwhyt;
	}

	public RsEntityResult getDydhb() {
		return dydhb;
	}

	public void setDydhb(RsEntityResult dydhb) {
		this.dydhb = dydhb;
	}

	public RsEntityResult getZbwyh() {
		return zbwyh;
	}

	public void setZbwyh(RsEntityResult zbwyh) {
		this.zbwyh = zbwyh;
	}
}



