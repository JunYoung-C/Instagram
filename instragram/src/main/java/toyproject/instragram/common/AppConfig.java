package toyproject.instragram.common;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import toyproject.instragram.common.auth.SignInArgumentResolver;
import toyproject.instragram.common.auth.SignInInterceptor;

import javax.persistence.EntityManager;
import java.util.List;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new SignInInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/javascript/**", "/images/**", "/error", "/",
                        "/members/signin", "/members/signout", "/members/signup", "/*.ico");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SignInArgumentResolver());
    }
}
