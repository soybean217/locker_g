package com.highguard.Wisdom.mgmt.hibernate.beans;

import java.util.Date;

/**
 * IC卡管理
 * @author huangqm
 *
 */
public class ICPort {

	private Integer id;
	private String iccard;
	private String name;
	private String thumb;
	private String createtime;
	private Lattice lattice;
	private String description;
	private Integer num;
	private Integer type;
	private double price;
	private double oldprice;

	private String outcardnumber; //
	 
	public String getOutcardnumber() {
		return outcardnumber;
	}
	public void setOutcardnumber(String outcardnumber) {
		this.outcardnumber = outcardnumber;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Lattice getLattice() {
		return lattice;
	}
	public void setLattice(Lattice lattice) {
		this.lattice = lattice;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIccard() {
		return iccard;
	}
	public void setIccard(String iccard) {
		this.iccard = iccard;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public double getOldprice() {
		return oldprice;
	}

	public void setOldprice(double oldprice) {
		this.oldprice = oldprice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
