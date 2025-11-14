package ru.mentee.library.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanPostProcessorConfig implements BeanPostProcessor {
  private static final Logger logger = LoggerFactory.getLogger(BeanPostProcessorConfig.class);

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    logger.debug("Before initialization: {}", beanName);
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    logger.debug("After initialization: {}", beanName);
    return bean;
  }
}
