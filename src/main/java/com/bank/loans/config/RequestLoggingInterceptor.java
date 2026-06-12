package com.bank.loans.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingInterceptor.class);
    private static final String START_TIME_ATTR = "request.start.time";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute(START_TIME_ATTR, System.nanoTime());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Long startTime = (Long) request.getAttribute(START_TIME_ATTR);
        if (startTime == null) {
            return;
        }

        long durationNs = System.nanoTime() - startTime;
        long durationMs = durationNs / 1_000_000;

        String method = request.getMethod();
        String uri = request.getRequestURI();
        int status = response.getStatus();

        if (status >= 500) {
            log.error("{} {} → {} in {}ms", method, uri, status, durationMs);
        } else if (status >= 400) {
            log.warn("{} {} → {} in {}ms", method, uri, status, durationMs);
        } else {
            log.info("{} {} → {} in {}ms", method, uri, status, durationMs);
        }
    }
}
