package cn.zjt.iot.oncar.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cn.zjt.iot.oncar.server.service.HeartRateService;
import cn.zjt.iot.oncar.server.util.SecurityUtil;

/**
 * Servlet implementation class DownloadHeartRateModelServlet
 */
@WebServlet("/DownloadHeartRateModelServlet")
public class DownloadHeartRateModelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadHeartRateModelServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/plain;charset=utf-8");
		
		int id = Integer.parseInt(request.getParameter("id"));
		PrintWriter pw = response.getWriter();
		
		JSONObject returnPack = new JSONObject();
		
		HeartRateService heartRateService = new HeartRateService();
		heartRateService.DownloadHeartRateModelService(id, returnPack);
		pw.print(SecurityUtil.Encode(returnPack.toString()));
		
		System.out.println(id);
		System.out.println(returnPack);
		
		pw.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
