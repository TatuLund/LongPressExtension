package org.vaadin.addons.longpressextension.demo;

import org.vaadin.addons.longpressextension.LongPressExtension;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Theme("demo")
@Title("LongPressExtension Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {

        Label label = new Label("Click and hold 5 sec");
        label.addStyleName(ValoTheme.LABEL_LARGE);
        final LongPressExtension ext = new LongPressExtension(label);
        ext.setTimeout(5000);
        ext.addLongPressListener(event -> {
            Notification.show("Pressed");
            label.addStyleName(ValoTheme.LABEL_SUCCESS);
        });

        final VerticalLayout layout = new VerticalLayout();
        layout.setStyleName("demoContentLayout");
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.addComponent(label);
        layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
        setContent(layout);
    }

}
