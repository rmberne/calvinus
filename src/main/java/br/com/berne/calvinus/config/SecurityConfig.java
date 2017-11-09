package br.com.berne.calvinus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration
    .WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration
    .EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

@EnableAuthorizationServer
@EnableOAuth2Client
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  @Qualifier("oauth2ClientContext")
  private OAuth2ClientContext oauth2ClientContext;

  private Filter ssoFilter() {
    OAuth2ClientAuthenticationProcessingFilter facebookFilter =
        new OAuth2ClientAuthenticationProcessingFilter("/login/facebook");
    OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
    facebookFilter.setRestTemplate(facebookTemplate);
    UserInfoTokenServices tokenServices =
        new UserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId());
    tokenServices.setRestTemplate(facebookTemplate);
    facebookFilter.setTokenServices(tokenServices);
    tokenServices.setAuthoritiesExtractor(authoritiesExtractor(facebookTemplate));
    return facebookFilter;
  }

  public AuthoritiesExtractor authoritiesExtractor(OAuth2RestOperations template) {
    return map -> {
      String url = (String) map.get("organizations_url");
      @SuppressWarnings("unchecked")
      List<Map<String, Object>> orgs = template.getForObject(url, List.class);
      if (orgs.stream().anyMatch(org -> "spring-projects".equals(org.get("login")))) {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
      }
      throw new BadCredentialsException("Not in Spring Team");
    };
  }



  @Bean
  @ConfigurationProperties("facebook.client")
  public AuthorizationCodeResourceDetails facebook() {
    return new AuthorizationCodeResourceDetails();
  }

  @Bean
  @ConfigurationProperties("facebook.resource")
  public ResourceServerProperties facebookResource() {
    return new ResourceServerProperties();
  }

  @Bean
  public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(filter);
    registration.setOrder(-100);
    return registration;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .antMatcher("/**")
        .authorizeRequests()
        .antMatchers("/", "/login**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
        .exceptionHandling()
        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
        .and().logout().logoutSuccessUrl("/").permitAll();
  }

}
