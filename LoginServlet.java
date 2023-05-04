
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String user = request.getParameter("username");
            String pass = request.getParameter("password");
            
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("pass", pass);

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "1234");

                PreparedStatement ps = con.prepareStatement("SELECT pass FROM login WHERE user = ?");
                ps.setString(1, user);

                ResultSet rs = ps.executeQuery();
//                out.print(ps.toString());

                if (rs.next()) {
                    String fetchedPass = rs.getString("pass");
                    if (pass.equals(fetchedPass)) {
                        response.sendRedirect("./html/MiddlePage.jsp");
                    } else {
                        // Incorrect password
                        request.setAttribute("errorMessage", "<script>alert('Incorrect password');</script>");
                        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                        rd.forward(request, response);
                    }
                } else {
                    request.setAttribute("errorMessage", "<script>alert('User name not exists.');</script>");
                    RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                    rd.forward(request, response);
                }

            } catch (ClassNotFoundException | SQLException ex) {

                out.print("An error occurred: " + ex.getMessage());
            }
        }
    }
}
