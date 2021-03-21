package io.kimmking.rpcfx.demo.provider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import io.kimmking.rpcfx.api.RpcfxResolver;

public class DemoResolver implements RpcfxResolver, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object resolve(final String serviceClass) {
        return applicationContext.getBean(serviceClass);
    }

    @Override
    public Object resolve(final Class<?> serviceClass) {
        return applicationContext.getBean(serviceClass);
    }
}
