package visa.vttp.csf.smackBE.config;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTAuthFilter extends OncePerRequestFilter {
  private static final Logger logger = LoggerFactory.getLogger(JWTAuthFilter.class);
  private final UserDetailsService uSvc;

  private static JWTVerifier verifier;
  private static String AUTH_SECRET;
  private static long AUTH_DURATION_VALID;

  public JWTAuthFilter(UserDetailsService uSvc, JWTAuthProps props) {
    this.uSvc = uSvc;
    AUTH_SECRET = props.AUTH_SECRET;
    AUTH_DURATION_VALID = props.AUTH_DURATION_VALID;
    verifier = JWT.require(Algorithm.HMAC512(AUTH_SECRET)).build();
    logger.info(">> AUTH SECRET: " + AUTH_SECRET);
    logger.info(">> AUTH DURATION VALID: " + AUTH_DURATION_VALID);
  }

  @PostConstruct
  public void init() {
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    logger.info(">>> reqeust url: " + request.getRequestURL());
    logger.info(">>> HEADER INFO: " + header);
    if (header == null || header.equals("") || !header.startsWith("Bearer ")) {
      logger.info(">>> NO HEADER INFO, CONTINUING");
      filterChain.doFilter(request, response);
      return;
    }

    final String token = header.split(" ")[1].trim();
    if (!validateToken(token)) {
      filterChain.doFilter(request, response);
      return;
    }

    logger.info(">>> USERNAME: " + getUsername(token));
    UserDetails user = uSvc.loadUserByUsername(getUsername(token));

    AbstractAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,
        user == null ? Collections.emptyList() : user.getAuthorities());
    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authToken);

    filterChain.doFilter(request, response);
  }

  public static String generateToken(UserDetails user) {
    return "Bearer " + JWT.create().withSubject(user.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + AUTH_DURATION_VALID))
        .sign(Algorithm.HMAC512(AUTH_SECRET));
  }

  public static boolean validateToken(String token) {
    try {
      DecodedJWT djwt = verifier.verify(token);
      if (null != djwt.getExpiresAt() && new Date().before(djwt.getExpiresAt())) {
        return true;
      }
    } catch (JWTVerificationException e) {
      logger.error("Failed to verify JWT");
      throw new RuntimeException("Failed to verify JWT", e);
    }
    return false;
  }

  public static String getUsername(String token) {
    DecodedJWT djwt = JWT.decode(token);
    return djwt.getClaims().get("sub").asString();
  }

}
