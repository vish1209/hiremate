
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class saveDetailsOfJobseeker extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String name = request.getParameter("username");
            String email = request.getParameter("email");
            String add = request.getParameter("add");
            long num = Long.parseLong(request.getParameter("number"));
            int age = Integer.parseInt(request.getParameter("age"));
            String clg = request.getParameter("clg");
            String branch = request.getParameter("branch");
            int year = Integer.parseInt(request.getParameter("year"));
            String skills = request.getParameter("skills");
            
            HttpSession session = request.getSession();

            session.setAttribute("username", name);
            session.setAttribute("email", email);
            session.setAttribute("add", add);
            session.setAttribute("num", num);
            session.setAttribute("age", age);
            session.setAttribute("clg", clg);
            session.setAttribute("branch", branch);
            session.setAttribute("year", year);
            session.setAttribute("skills", skills);
            session.setAttribute("title", "");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "1234");

                PreparedStatement pstmt = con.prepareStatement("insert into employee_profile values(?,?,?,?,?,?,?,?,?,?);");
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setString(3, add);
                pstmt.setLong(4, num);
                pstmt.setInt(5, age);
                pstmt.setString(6, clg);
                pstmt.setString(7, branch);
                pstmt.setInt(8, year);
                pstmt.setString(9, skills);
                pstmt.setString(10, "");

                pstmt.executeUpdate();
                
                out.print("<script>alert('applied successfully...');</script>");
                request.getRequestDispatcher("./html/jobmenu.jsp").forward(request, response);

            } catch (ClassNotFoundException | SQLException e) {
            }

        }
    }
}
