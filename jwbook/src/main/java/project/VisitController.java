package project;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Servlet implementation class VisitController
 */
@WebServlet(urlPatterns = "/visit.nhn")
public class VisitController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private VisitDAO dao;
	private ServletContext ctx;
	
	private final String START_PAGE = "project/visitList.jsp";
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = new VisitDAO();
		ctx = getServletContext();		
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		
		dao = new VisitDAO();
		
		Method m;
		String view = null;
		
		if (action == null) {
			action = "listVisit";
		}
		try {
			m = this.getClass().getMethod(action, HttpServletRequest.class);
			view = (String)m.invoke(this, request);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			ctx.log("요청 action 없음!!");
			request.setAttribute("error", "action 파라미터가 잘못 되었습니다!!");
			view = START_PAGE;
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		if(view.startsWith("redirect:/")) {
			String rview = view.substring("redirect:/".length());
			response.sendRedirect(rview);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher(view);
			dispatcher.forward(request, response);	
		}
	}
    
    public String addVisit(HttpServletRequest request) {
		Visit n = new Visit();
		try {							        
			BeanUtils.populate(n, request.getParameterMap());
			dao.addVisit(n);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("방명록 추가 과정에서 문제 발생!!");
			request.setAttribute("error", "방명록이 정상적으로 등록되지 않았습니다!!");
			return listVisit(request);
		}
		return "redirect:/visit.nhn?action=listVisit";
		
	}
    public String modVisit(HttpServletRequest request) {
    	int aid = Integer.parseInt(request.getParameter("aid"));
		Visit n = new Visit();	
		try {							        
			BeanUtils.populate(n, request.getParameterMap());
			dao.modVisit(n,aid);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("방명록 수정 과정에서 문제 발생!!");
			request.setAttribute("error", "방명록 수정이 정상적으로 처리되지 않았습니다!!");
			return listVisit(request);
		}
		
		return "redirect:/visit.nhn?action=listVisit";		
	}
	public String listVisit(HttpServletRequest request) {
    	List<Visit> list;
		try {
			list = dao.getAll();
	    	request.setAttribute("visitlist", list);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("방명록 생성 과정에서 문제 발생!!");
			request.setAttribute("error", "방명록이 정상적으로 처리되지 않았습니다!!");
		}
    	return "project/visitList.jsp";
    }
    
    public String getVisit(HttpServletRequest request) {
        int aid = Integer.parseInt(request.getParameter("aid"));
        try {
			Visit n = dao.getVisit(aid);
			request.setAttribute("visit", n);
		} catch (SQLException e) {
			e.printStackTrace();
			ctx.log("방명록을 가져오는 과정에서 문제 발생!!");
			request.setAttribute("error", "방명록을 정상적으로 가져오지 못했습니다!!");
		}

    	return "project/modView.jsp";
    }
}
