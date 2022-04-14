package toyproject.instragram.common;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignInInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        if (!isSignedIn(session)) {
            response.sendRedirect("/members/signin");
            return false;
        }

        return true;
    }

    private boolean isSignedIn(HttpSession session) {
        return session != null && session.getAttribute(SessionConst.SIGN_IN_MEMBER) != null;
    }
}
