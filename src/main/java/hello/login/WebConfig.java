package hello.login;

import hello.login.web.argumentresolver.LoginMemberArgumentResolver;
import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    /*
    *  [ FilterRegistrationBean ]
    *
    *   필터를 등록하는 방법은 여러가지가 있지만, spring boot를 사용한다면 FilterRegistrationBean을 사용해서 등록하면 된다.
    *
    *   setFilter(new LogFilter()) -> 등록할 필터를 지정한다.
    *   setOrder(1) -> 필터는 체인으로 동작한다. 따라서 순서가 필요하다. 낮을 수록 먼저 동작한다.
    *   addUrlPatterns("/*") -> 필터를 적용할 URL 패턴을 지정한다. 한번에 여러 패턴을 지정할 수 있다.
    *
    *
    *   [참고]
    *
    * @ServletComponentScan @WebFilter(filterName = "logFilter" , urlPatterns = "/*")로 필터 등록이 가능하지만
    * 필터 순서 조절이 안된다. 따라서 FilterRegistrationBean을 사용하자.
    * */

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()) // interceptor 를 등록한다.
                .order(1) // interceptor 호출 sequence 를 지정한다. under 일수록 먼저 호출된다.
                .addPathPatterns("/**") // interceptor 를 적용할 URL Pattern 을 지정한다.
                .excludePathPatterns("/css/**", "/*.ico", "/error"); // interceptor 에서 제외할 Pattern 을 지정한다.

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/add", "/login", "/logout",
                        "/css/**", "/*.ico", "/error");
    }
    // Filter 와 비교해보면 interceptor 는 addPathPatterns, excludePathPatterns 로 매우 정밀하게 URL Pattern을 지정할 수 있다.
    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

//    @Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
