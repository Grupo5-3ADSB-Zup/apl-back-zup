package school.sptech.zup.configuration.security;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import school.sptech.zup.configuration.security.jwt.GerenciadorTokenJwt;
import school.sptech.zup.service.AutenticacaoJWT.AutenticacaoService;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguracao {
    private static  final String ORIGENS_PERMITIDAS = "*";

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private AutenticacaoEntryPoint autenticacaoJwtEntryPoint;

    private static final AntPathRequestMatcher[] URLS_PERMITIDAS = {
            new AntPathRequestMatcher("/swagger-resources"),
            new AntPathRequestMatcher("/swagger-resources/**"),
            new AntPathRequestMatcher("/configuration/ui"),
            new AntPathRequestMatcher("/configuration/security"),
            new AntPathRequestMatcher("/swagger-ui.html"),
            new AntPathRequestMatcher("/webjars/**"),
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/api-public/**"),
            new AntPathRequestMatcher("/api-public/authenticate"),
            new AntPathRequestMatcher("/actuador/*"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/usuarios/login/**"),
            new AntPathRequestMatcher("/usuario/id"),
            new AntPathRequestMatcher("/usuario/{id}"),
            //new AntPathRequestMatcher("/usuario/user/comum"),
            //new AntPathRequestMatcher("/usuario/user/empresa"),
            //new AntPathRequestMatcher("/usuario/user/admin"),
            new AntPathRequestMatcher("/usuario/foto/**"),
            new AntPathRequestMatcher("/noticia/rss"),
            new AntPathRequestMatcher("/noticia/rss/info"),
            new AntPathRequestMatcher("/noticia/**"),
            new AntPathRequestMatcher("/cadastro/**"),
            new AntPathRequestMatcher("/mobile/**"),
            new AntPathRequestMatcher("/login/logar"),
            new AntPathRequestMatcher("/login/usuario/*"),
            new AntPathRequestMatcher("/h2-console"),
            new AntPathRequestMatcher("/error/**"),
            new AntPathRequestMatcher("/admin/**"),
            new AntPathRequestMatcher("jdbc:mysql://localhost:3306/zup?useSSL=false&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true"),
            new AntPathRequestMatcher("jdbc:sqlserver://projeto-zup.database.windows.net:1433;database=bd-projeto-zup;user=admin-zup@projeto-zup;password=#Gfgrupo5;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30"),
            new AntPathRequestMatcher("/3001/**"),
            new AntPathRequestMatcher("/3000/**"),
            new AntPathRequestMatcher("https://apl-front-zup-teste-git-prodution-vercel-zup.vercel.app/home"),
            new AntPathRequestMatcher("/IA/**")
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers()
                .frameOptions().disable()
                .and()
                .cors()
                .configurationSource(request -> buildCorsConfiguration())
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(URLS_PERMITIDAS)
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .exceptionHandling()
                .authenticationEntryPoint(autenticacaoJwtEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authManeger(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(new
                AutenticacaoProvider(autenticacaoService, passwordEncoder()));
        return authenticationManagerBuilder.build();
    }

    @Bean
    public AutenticacaoEntryPoint jwtAuthenticationEntryPointBean(){
        return new AutenticacaoEntryPoint();
    }

    @Bean
    public AutenticacaoFilter jwtAuthenticationFilterBean() {
        return new AutenticacaoFilter(autenticacaoService, jwtAuthenticationUtilBean());
    }

    @Bean
    public GerenciadorTokenJwt jwtAuthenticationUtilBean(){
        return new GerenciadorTokenJwt();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private CorsConfiguration buildCorsConfiguration(){
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(Collections.singletonList(ORIGENS_PERMITIDAS));
        configuration.setAllowedMethods(
                Arrays.asList(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name())
                );
        configuration.setAllowedHeaders(Arrays.asList(HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION));
        return configuration;
    }
}
