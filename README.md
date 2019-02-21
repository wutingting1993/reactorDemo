# WebFluxTest
- @WebFluxTest helps to test Spring WebFlux controllers with auto-configuring the Spring WebFlux infrastructure, 
limit scanned beans like {@Controller, @ControllerAdvice, @JsonComponent, WebFluxConfigurer} and never scan @Component  or @Service or @Repository beans. 
- Typically {@code @WebFluxTest} is used in combination with {@link MockBean @MockBean}
or {@link Import @Import} to create any collaborators required by your
{@code @Controller} beans.
