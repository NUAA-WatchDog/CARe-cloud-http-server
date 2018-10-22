package cn.zjt.iot.oncar.server.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cn.zjt.iot.oncar.server.service.UserService;
import cn.zjt.iot.oncar.server.util.SecurityUtil;

/**
 * Servlet implementation class CreateUserServlet
 */
@WebServlet("/CreateUserServlet")
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/plain;charset=utf-8");
		
		BufferedReader br = request.getReader();
		PrintWriter pw = response.getWriter();
		
		String input = br.readLine();
		String inputDecode = SecurityUtil.Decode(input);
		JSONObject inputPack = new JSONObject(inputDecode);
		JSONObject returnPack = new JSONObject();
		
		UserService userService = new UserService();
		userService.CreateUserService(inputPack, returnPack);
		pw.print(SecurityUtil.Encode(returnPack.toString()));
		
		System.out.println(inputDecode);
		System.out.println(returnPack.toString());
		
		br.close();
		pw.close();
	}

}
