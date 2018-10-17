package io.github.rxcats.springcloudzuul;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVICE_ID_KEY;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import lombok.extern.slf4j.Slf4j;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Slf4j
@Component
public class JwtTokenAuthenticationFilter extends ZuulFilter {

    @Value("${jwt.secret-code}")
    private String secretCode;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER + 1; // run before PreDecoration
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return !StringUtils.isEmpty(ctx.getRequest().getHeader("authorization"));
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();

        var serviceId = (String) ctx.get(SERVICE_ID_KEY);
        log.info("##serviceId:{}", serviceId);

        String token = ctx.getRequest().getHeader("authorization");
        verifyToken(token);

        return null;
    }

    private void verifyToken(String token) {
        log.info("##token:{}", token);
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(secretCode.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token);
            String id = claimsJws.getBody().getId();
            log.info("##id:{}", id);
        } catch (Exception e) {
            throw new RuntimeException("Invalid authorization:" + token);
        }
    }
}
