package 과제2;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.beanutils.BeanUtils;

@WebServlet("/rooms")
public class RoomsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private RoomsDAO dao;
	private ServletContext ctx;
	
	private final String START_PAGE = "과제2/roomsList.jsp";
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = new RoomsDAO();
		ctx = getServletContext();		
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		
		dao = new RoomsDAO();
		
		Method m;
		String view = null;
		
		if (action == null) {
			action = "listRooms";
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
    
    public String addRooms(HttpServletRequest request) {
		Rooms r = new Rooms();
		try {						
			BeanUtils.populate(r, request.getParameterMap());
			
			dao.addRooms(r);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("방명록 추가 과정에서 문제 발생!!");
			request.setAttribute("error", "방명록이 정상적으로 등록되지 않았습니다!!");
			return listRooms(request);
		}
		
		return "redirect:/rooms?action=listRooms";
		
	}

	public String listRooms(HttpServletRequest request) {
    	List<Rooms> list;
		try {
			list = dao.getAll();
	    	request.setAttribute("roomslist", list);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("방명록 목록 생성 과정에서 문제 발생!!");
			request.setAttribute("error", "방명록 목록이 정상적으로 처리되지 않았습니다!!");
		}
    	return "과제2/roomsList.jsp";
    }
	
	 
    public String getRooms(HttpServletRequest request) {
        int aid = Integer.parseInt(request.getParameter("aid"));
        try {
			Rooms r = dao.getRooms(aid);
			request.setAttribute("rooms", r);
		} catch (SQLException e) {
			e.printStackTrace();
			ctx.log("방명록을 가져오는 과정에서 문제 발생!!");
			request.setAttribute("error", "방명록을 정상적으로 가져오지 못했습니다!!");
		}

    	return "과제2/roomsModify.jsp";
    }
    
    
    public String moveView(HttpServletRequest request) {
    	return "과제2/roomsView.jsp";
    }
    
    public String cancelRooms(HttpServletRequest request) {
    	 int aid = Integer.parseInt(request.getParameter("aid"));
         try {
 			Rooms r = dao.getRooms(aid);
 			request.setAttribute("rooms", r);
 		} catch (SQLException e) {
 			e.printStackTrace();
 			ctx.log("방명록을 가져오는 과정에서 문제 발생!!");
 			request.setAttribute("error", "방명록을 정상적으로 가져오지 못했습니다!!");
 		}
        
        return "과제2/roomsModify.jsp";
    }
    
    public String modifyRooms(HttpServletRequest request) {
    	int aid = Integer.parseInt(request.getParameter("aid"));
        Rooms r = new Rooms();   
        try {                             
           BeanUtils.populate(r, request.getParameterMap());
           dao.modifyRooms(aid,r);
        } catch (Exception e) {
           e.printStackTrace();
           ctx.log("방명록 수정 과정에서 문제 발생!!");
           request.setAttribute("error", "방명록 수정이 정상적으로 처리되지 않았습니다!!");
           return listRooms(request);
        }

        
        return "redirect:/rooms?action=listRooms";
    }
}
