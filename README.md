# WebFluxTest
- @WebFluxTest helps to test Spring WebFlux controllers with auto-configuring the Spring WebFlux infrastructure, 
limit scanned beans like {@Controller, @ControllerAdvice, @JsonComponent, WebFluxConfigurer} and never scan @Component  or @Service or @Repository beans. 
- Typically {@code @WebFluxTest} is used in combination with {@link MockBean @MockBean}
or {@link Import @Import} to create any collaborators required by your
{@code @Controller} beans.
# @EnableWebFlux
You can use the @EnableWebFlux annotation in your Java config, as the following example shows:
```
@Configuration
@EnableWebFlux
public class WebConfig {
}
```
The preceding example registers a number of Spring WebFlux infrastructure beans and adapts to dependencies available on the classpath — for JSON, XML, and others.

#### [What is the function of the @EnableWebFlux annotation](https://stackoverflow.com/questions/51843344/what-is-the-function-of-the-enablewebflux-annotation)

As stated in the [Spring Boot reference documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html#boot-features-webflux-auto-configuration), @EnableWebFlux will tell Spring Boot that you wish to take full control over the WebFlux configuration and disable all auto-configuration for this (including static resources).

@EnableWebFlux doesn't configure Freemarker, it actually sets up the whole WebFlux infrastructure. In the case of Spring Boot, adding the spring-boot-starter-freemarker as a dependency (and optionally configuring it through configuration properties) is all you need.
