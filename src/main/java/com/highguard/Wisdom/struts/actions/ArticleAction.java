package com.highguard.Wisdom.struts.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
@SuppressWarnings("unchecked")
@Controller
@Scope("prototype")
public class ArticleAction extends BaseAction  {
   
	      private List<Article> articleList = new ArrayList<Article>();  
	      private Article article;  
	      private int id;  
	      private String state;  
	      private String htmlName;  
	      public String getHtmlName() {
			return htmlName;
		}

		public void setHtmlName(String htmlName) {
			this.htmlName = htmlName;
		}

		public List<Article> getArticleList() {
			return articleList;
		}

		public void setArticleList(List<Article> articleList) {
			this.articleList = articleList;
		}

		public Article getArticle() {
			return article;
		}

		public void setArticle(Article article) {
			this.article = article;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public List<Article> list(){
	    	  for (int i = 0; i <5; i++) {
	    		  Article  article1 = new Article();
	    		  article1.setId(i);
	    		  article1.setTitle("haha"+i);
	    		  article1.setContext("enenen"+i);
	    		  article1.setState("1");
	    		  articleList.add(article1);
			}
	    	  return articleList;
	      }
	        
	      public String listArticles() throws Exception{ 
	    	  	  articleList=list();
			
	          return SUCCESS;  
	      }  
	        
	      public String showArticle() throws Exception{  
	          //静态化文件名称，我们以article_作为前缀，后面连接上articleID，让其独一无二；  
	          this.htmlName = "article_"+1;  
	          //如果已经静态化，则直接访问静态化页面  
	          if("0".equals(state)){  
	              return SUCCESS;  
	          }  
	         //原本此次操作应该访问的article.jsp页面  
	          String jsp_url = "/pages/article.jsp";  
	          //要生成的静态化文件路径(我们统一放在html目录下)  
	          String html_url = getRequest().getSession().getServletContext().getRealPath("")+"//pages//html//"+this.htmlName+".html";  
	          //从数据库里读取Article数据
	          article = new Article();
	          article.setId(1);
	          article.setTitle("aaa");
	          article.setContext("aaaaaaaaaaaaaaaaaaaaaaaaa");
	          getRequest().setAttribute("article",article);  
	          //关键是这步，createHTML Mapping()方法将原本输出到JSP的输出流转到静态页去了  
	          CreateStaticHTML.createHTML4Mapping(getRequest(),getResponse(), jsp_url, html_url);  
	         // htmlName = "1";
	          return SUCCESS;  
	      }  
	        
	      public HttpServletRequest getRequest(){  
	          return ServletActionContext.getRequest();  
	      }  
	        
	      public HttpServletResponse getResponse(){  
	          return ServletActionContext.getResponse();  
	      }  

}
