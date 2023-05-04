import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String user = request.getParameter("username");
            String pass = request.getParameter("password");
            String rpass = request.getParameter("repassword");
            String sql = "insert into login values('" + user + "','" + pass + "');";
            request.setAttribute("errorMessage", "");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "1234");

                Statement s = con.createStatement();
                ResultSet rs = s.executeQuery("select pass from login where user='"+user+"';");
                
                if (rs.next()) {
                    request.setAttribute("errorMessage", "<script>alert('username already exists');</script>");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
//                    response.sendRedirect("index.jsp");
                } else {
                    s.executeUpdate(sql);
                    response.sendRedirect("index.jsp");
                }

            } catch (ClassNotFoundException | SQLException ex) {
                out.print(sql);
            }
        }
    }
}
