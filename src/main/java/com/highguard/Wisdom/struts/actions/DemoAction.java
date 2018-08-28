package com.highguard.Wisdom.struts.actions;

import com.highguard.Wisdom.mgmt.hibernate.beans.Lattice;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.manager.DeviceManager;
import com.highguard.Wisdom.mgmt.manager.UserManager;
import com.highguard.Wisdom.struts.listener.ApplicationUtil;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ws on 2017/7/25.
 */
@Controller
@Scope("prototype")
public class DemoAction extends BaseAction {


    private List<Lattice> lattices;

    public String console(){
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();

        DeviceManager deviceManager = (DeviceManager) ApplicationUtil.act.getBean("deviceManager");
        UserManager userManager = (UserManager) ApplicationUtil.act.getBean("userManager");
        User user = userManager.getUserById(1);

        session.setAttribute("user", user);
        HashMap<String, String> map = new HashMap<>();
        lattices = deviceManager.getLatticeList(0,100, map);

        return SUCCESS;
    }

    public List<Lattice> getLattices() {
        return lattices;
    }

    public void setLattices(List<Lattice> lattices) {
        this.lattices = lattices;
    }
}
