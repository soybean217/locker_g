package com.highguard.Wisdom.mgmt.dao;

import org.springframework.stereotype.Repository;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ws on 2017/7/2.
 */
public class TestDao extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        PrintWriter printer = response.getWriter();
        printer.println("hello world");
    }
}
