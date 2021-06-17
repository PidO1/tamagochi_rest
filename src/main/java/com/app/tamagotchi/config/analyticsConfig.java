
import com.moesif.servlet.MoesifFilter;

import javax.servlet.Filter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.*;

@Configuration
public class analyticsConfig extends WebMvcConfigurerAdapter {

  @Bean
  public Filter moesifFilter() {
    return new MoesifFilter("eyJhcHAiOiI5MjoxODEiLCJ2ZXIiOiIyLjAiLCJvcmciOiIzNTk6MTQxIiwiaWF0IjoxNjIyNTA1NjAwfQ.ZJNj82jHP3Dc0giI8U1uBySB8jzsh9uJRM1Xz-MDHag");
  }
}