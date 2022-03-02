package ru.javaSchoolProject.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.javaSchoolProject.security.userData.MyUserDetails;
import ru.javaSchoolProject.security.userData.MyUserDetailsService;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;


@Component
@Log
@Order(0)
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION = "Authorization";

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
//        System.out.println(token);
//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//        System.out.println(httpServletRequest.getHeaderNames());
//        System.out.println("trying " + httpServletRequest.getHeader("Authorization"));
//        System.out.println("trying jwt " + httpServletRequest.getHeader("JWT"));
//        System.out.println(httpServletRequest.getHeader("user-agent"));
        if (token != null && jwtProvider.validateToken(token)) {//if token is valid we register the user and give him role (authority)
            String userLogin = jwtProvider.getLoginFromToken(token);
            MyUserDetails myUserDetails = myUserDetailsService.loadUserByUsername(userLogin);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }


}
