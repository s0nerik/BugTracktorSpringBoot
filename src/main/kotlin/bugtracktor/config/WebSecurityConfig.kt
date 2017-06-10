package bugtracktor.config

import bugtracktor.security.auth.RestAuthenticationEntryPoint
import bugtracktor.security.auth.TokenAuthenticationFilter
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import bugtracktor.service.CustomUserDetailsService
import org.springframework.context.annotation.Bean


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
        private val jwtUserDetailsService: CustomUserDetailsService,
        private val restAuthenticationEntryPoint: RestAuthenticationEntryPoint,
        private val authenticationSuccessHandler: AuthenticationSuccessHandler,
        private val authenticationFailureHandler: AuthenticationFailureHandler
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun jwtAuthenticationTokenFilter() = TokenAuthenticationFilter()

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService<CustomUserDetailsService>(jwtUserDetailsService)
    }

    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http
                .csrf().disable()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ).and()
                .exceptionHandling().authenticationEntryPoint( restAuthenticationEntryPoint ).and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), BasicAuthenticationFilter::class.java)
                        .authorizeRequests()
                            .anyRequest()
                            .authenticated()
                            .and()
                        .formLogin()
                            .permitAll()
                            .successHandler(authenticationSuccessHandler)
                            .failureHandler(authenticationFailureHandler)
                            .and()
                        .logout()
                            .permitAll()
                            .logoutSuccessUrl("/login")
        // @formatter:on
    }

}