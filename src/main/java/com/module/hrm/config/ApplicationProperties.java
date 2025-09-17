package com.module.hrm.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to HRM.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Liquibase liquibase = new Liquibase();

    // jhipster-needle-application-properties-property

    public Liquibase getLiquibase() {
        return liquibase;
    }

    // jhipster-needle-application-properties-property-getter

    public static class Liquibase {

        private Boolean asyncStart = true;

        public Boolean getAsyncStart() {
            return asyncStart;
        }

        public void setAsyncStart(Boolean asyncStart) {
            this.asyncStart = asyncStart;
        }
    }

    // jhipster-needle-application-properties-property-class

    @Getter
    private final Aws aws = new Aws();

    @Getter
    private final Sap sap = new Sap();

    @Getter
    private final Zalo zalo = new Zalo();

    @Getter
    private final Maps maps = new Maps();

    @Getter
    private final Evs evs = new Evs();

    @Getter
    private final SalesForce salesForce = new SalesForce();

    @Getter
    @Setter
    public static class Aws {

        private boolean keyEnabled = false;

        private String keyAccess = null;

        private String keySecret = null;

        private String region;

        private String s3bucket;

        private String s3bucketIr;

        private String s3bucketEvs;

        private String decompressionLambda;
    }

    @Getter
    @Setter
    public static class Sap {

        private String baseUrl = null;

        private String ioPath = null;
        private String poPath = null;

        private String sapClient = null;

        private String username = null;

        private String password = null;
    }

    @Getter
    @Setter
    public static class Zalo {

        private String accessUrl = null;

        private String tokenPath = null;

        private String znsUrl = null;

        private String miniAppUrl = null;

        private String templatePath = null;

        private String oaUrl = null;

        private String consultingPath = null;

        private String keySecret = null;

        private String appId = null;
    }

    @Getter
    @Setter
    public static class Maps {

        private String apiKey = null;
    }

    @Getter
    @Setter
    public static class Evs {

        private String baseUrl = null;
        private String invByEmpCodePath = null;
        private String invDetailByEmpCodePath = null;
        private String invInfoPath = null;
        private String keySecret = null;
    }

    @Getter
    @Setter
    public static class SalesForce {

        private String baseUrl = null;
        private String customerPath = null;
        private String distributorPath = null;
        private String promotionPath = null;
        private String productPath = null;
        private String orderPath = null;
        private String orderItemPath = null;
        private String loyaltyPath = null;
        private String customerLoyaltyPath = null;
        private String authUrl = null;
        private String authPath;
        private String clientId = null;
        private String clientSecret = null;
        private String username = null;
        private String password = null;
        private String securityToken = null;
    }
}
