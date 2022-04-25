package academy.devdojo.springboot2essentials.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


/**
 * Classe que configura a paginação padrão do spring sobrescrevendo a original.
 */
@Configuration
public class DefaultPageSizeWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver pageHandler = new PageableHandlerMethodArgumentResolver();
        pageHandler.setFallbackPageable(PageRequest.of(0, 5)); // Pagina 1 -> 5 elementos por página.
        resolvers.add(pageHandler);
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
