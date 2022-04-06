package toyproject.instragram;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import toyproject.instragram.entity.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SignInArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasSignInAnnotation = parameter.hasParameterAnnotation(SignIn.class);
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        return hasSignInAnnotation && hasMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }

        return session.getAttribute(SessionConst.SIGN_IN_MEMBER);
    }
}
