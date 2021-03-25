package kuwo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static kuwo.flac.downloadUrl;

@WebServlet(urlPatterns = "/")
public class KuwoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 设置响应类型:
        resp.setContentType("text/html");

        // 获取输出流:
        PrintWriter pw = resp.getWriter();

        String rid = req.getParameter("rid");

        if (rid == null) {
            resp.setContentType("application/json; charset=utf-8");
            // 写入响应:
            pw.write("{\"code\": 400, \"message\": \"rid is not found\"}");
            // 最后不要忘记flush强制输出:
            pw.flush();
            return;
        }

        String flac = downloadUrl(rid);

        if (flac.isBlank()) {
            resp.setContentType("application/json; charset=utf-8");
            // 写入响应:
            pw.write("{\"code\": 401, \"url\": null, \"message\": \"flac is not found\"}");
            // 最后不要忘记flush强制输出:
            pw.flush();
            return;
        }

        Pattern p = Pattern.compile(".*url=(.*?)sig.*");
        Matcher m = p.matcher(flac.replace("\r\n", ""));
        try {
            resp.setContentType("application/json; charset=utf-8");
            if (m.matches()) {
                String g1 = m.group(1);
                // 写入响应:
                pw.write("{\"code\": 200, \"url\": \"" + g1 + "\"}");
                // 最后不要忘记flush强制输出:
                pw.flush();
            } else {
                throw new Exception("error");
            }
            return;
        } catch (Exception e) {
            resp.setContentType("application/json; charset=utf-8");
            // 写入响应:
            pw.write("{\"code\": 401, \"message\": \"flac is not found\"}");
            // 最后不要忘记flush强制输出:
            pw.flush();
            return;
        }
    }
}
