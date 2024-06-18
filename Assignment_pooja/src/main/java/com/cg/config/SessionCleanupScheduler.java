package com.cg.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;
@Component
public class SessionCleanupScheduler {

	 private static final Logger logger = LoggerFactory.getLogger(SessionCleanupScheduler.class);

    @Scheduled(fixedDelay = 60000) // Run every 60 seconds (1 minute)
    public void cleanupExpiredSessionAttributes() {
    	
    	logger.info("Starting session attribute cleanup task...");
    	
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);

        if (session != null) {
            Object successMessage = session.getAttribute("successMessage");
            if (successMessage != null) {
                session.removeAttribute("successMessage");
                logger.debug("Removed 'successMessage' attribute from session.");
            }
        }
        logger.info("Session attribute cleanup task completed.");
    }
}
