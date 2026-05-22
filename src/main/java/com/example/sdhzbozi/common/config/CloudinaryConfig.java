package com.example.sdhzbozi.common.config;

import com.cloudinary.Cloudinary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

@Configuration
public class CloudinaryConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CloudinaryConfig.class);
    private static final String CLOUDINARY_URL_SCHEME = "cloudinary://";

    @Bean
    @Conditional(CloudinaryUrlCondition.class)
    public Cloudinary cloudinary(@Value("${cloudinary.url:}") String cloudinaryUrl) {
        return new Cloudinary(cloudinaryUrl.trim());
    }

    @Bean
    public ApplicationRunner cloudinaryConfigurationWarning(@Value("${cloudinary.url:}") String cloudinaryUrl) {
        return args -> {
            if (StringUtils.hasText(cloudinaryUrl) && !hasCloudinaryScheme(cloudinaryUrl)) {
                LOGGER.warn("Ignoring cloudinary.url because it must start with '{}'. Image uploads are disabled.", CLOUDINARY_URL_SCHEME);
            }
        };
    }

    private static boolean hasCloudinaryScheme(String cloudinaryUrl) {
        return StringUtils.hasText(cloudinaryUrl) && cloudinaryUrl.trim().startsWith(CLOUDINARY_URL_SCHEME);
    }

    static class CloudinaryUrlCondition implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            String cloudinaryUrl = context.getEnvironment().getProperty("cloudinary.url", "");
            return hasCloudinaryScheme(cloudinaryUrl);
        }
    }

}
