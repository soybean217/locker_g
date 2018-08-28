package com.highguard.Wisdom.struts.actions;

import java.io.*;
import java.text.DateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.highguard.Wisdom.mgmt.hibernate.beans.Lattice;
import com.highguard.Wisdom.mgmt.manager.DeviceManager;
import com.highguard.Wisdom.util.DateUtil;
import com.highguard.Wisdom.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.record.formula.functions.Int;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.mgmt.hibernate.beans.ICPort;
import com.highguard.Wisdom.mgmt.manager.IcportManager;

@SuppressWarnings("unchecked")
@Controller
@Scope("prototype")
public class ICPostAction extends BaseAction {

	@Resource
	IcportManager icportManager;
	@Resource
	DeviceManager deviceManager;
	private String ids;
	private int id;
	private List<ICPort> icList =new ArrayList<ICPort>();

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<ICPort> getIcList() {
		return icList;
	}

	public void setIcList(List<ICPort> icList) {
		this.icList = icList;
	}


	private File thumb;

	private ICPort icPort;

	private Integer oldLatticeId = 0;
	private Integer lattice;

	private List<Lattice> lattices;
	/**
	 *人员列表
	 * 
	 * @return
	 */
	public String iCPortList() throws Exception {
		int currentPage = Integer.parseInt(getCurrentPage());
		int pageNum = Integer.parseInt(getPageNum());
		Map map = new HashMap();
		icList = icportManager.getICPortList(currentPage, pageNum, map);
		int total = icportManager.getICPortListCount(map);
		setTotalNum(String.valueOf(total));
		setTotalPages(String.valueOf(getTotalPage(total)));
		setRows(icList.size());
		return SUCCESS;
	}

	public String toeditIcport() throws Exception {
		if(id >0){//修改
			icPort = icportManager.get(id);
			oldLatticeId = icPort.getLattice() != null? icPort.getLattice().getId() :0;
		}else{//新增
            icPort = new ICPort();
            icPort.setCreatetime(DateUtil.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
		}
		lattices = deviceManager.getLatticeList();
		return SUCCESS;
	}

	/**
	 * 修改
	 *
	 * @return
	 * @throws Exception
	 */
	public String editIcport() throws Exception {


		if( thumb != null ) {

			String filename = StringUtil.getRandomFileName() + ".png";

			String path = ServletActionContext.getRequest().getRealPath("/upload");

			//输出流
			OutputStream os = new FileOutputStream(new File(path, filename));
			//输入流
			InputStream is = new FileInputStream(thumb);

			byte[] buf = new byte[1024];
			int length = 0;

			while (-1 != (length = is.read(buf))) {
				os.write(buf, 0, length);
			}
			is.close();
			os.close();

			icPort.setThumb("/upload/" + filename);
		}

		Lattice lt = deviceManager.getLatticeById(lattice);
		//检测这个板子上有没有玩具
		if( lt.getIcport() != null && !Objects.equals(lt.getIcport().getId(), icPort.getId())){
			throw new RuntimeException("该门内已经设置玩具 "+ lt.getIcport().getName() + " 请确保不要将两个玩具放到同一窗口内");
		}


		lt.setIcport(icPort);
		icPort.setLattice(lt);

		icportManager.saveICPort(icPort);

		return SUCCESS;
	}

	public String deleteICPort() throws Exception {
		icportManager.deleteICPort(ids);
		return SUCCESS;
	}

	public int getId(){
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ICPort getIcPort(){
		return icPort;
	}
	public void setIcPort(ICPort icPort){
		this.icPort = icPort;
	}

	public File getThumb() {
		return thumb;
	}

	public void setThumb(File thumb) {
		this.thumb = thumb;
	}


	public List<Lattice> getLattices() {
		return lattices;
	}

	public void setLattices(List<Lattice> lattices) {
		this.lattices = lattices;
	}

	public Integer getLattice() {
		return lattice;
	}

	public void setLattice(Integer lattice) {
		this.lattice = lattice;
	}

	public Integer getOldLatticeId() {
		return oldLatticeId;
	}

	public void setOldLatticeId(Integer oldLatticeId) {
		this.oldLatticeId = oldLatticeId;
	}
}
