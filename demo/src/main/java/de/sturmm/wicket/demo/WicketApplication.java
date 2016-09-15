package de.sturmm.wicket.demo;

import de.sturmm.wicket.demo.pages.HomePage;
import de.sturmm.wicket.nonstickysessions.NonStickySessionsModule;
import org.apache.wicket.DefaultPageManagerProvider;
import org.apache.wicket.Page;
import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.pageStore.memory.HttpSessionDataStore;
import org.apache.wicket.pageStore.memory.PageNumberEvictionStrategy;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.wicketstuff.pageserializer.fast2.Fast2WicketSerializer;

/**
 * Created by sturmm on 12.09.16.
 */
public class WicketApplication extends WebApplication implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Value("@${random.uuid}")
    private String instanceId;

    @Override
    protected void init() {
        super.init();
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, applicationContext));
        NonStickySessionsModule.setup(this, getPageManagerContext());
        getFrameworkSettings().setSerializer(new Fast2WicketSerializer());
    }
    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
