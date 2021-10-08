package org.vaadin.addons.tatu.longpressextension.client;

import org.vaadin.addons.tatu.longpressextension.LongPressExtension;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.shared.ui.Connect;

@Connect(LongPressExtension.class)
public class LongPressExtensionConnector extends AbstractExtensionConnector {

    LongPressExtensionServerRpc rpc = RpcProxy
            .create(LongPressExtensionServerRpc.class, this);
    boolean started = false;
    Timer t;

    @Override
    protected void extend(ServerConnector target) {
        final Widget widget = ((ComponentConnector) target).getWidget();
        widget.addDomHandler(event -> {
            handleMouseDown();
        }, MouseDownEvent.getType());
        widget.addDomHandler(event -> {
            handleMouseDown();
        }, TouchStartEvent.getType());
        widget.addDomHandler(event -> {
            handleMouseUp(widget, event);
        }, MouseUpEvent.getType());
        widget.addDomHandler(event -> {
            handleTouchEnd(widget, event);
        }, TouchEndEvent.getType());
    }

    private void handleMouseDown() {
        started = true;
        // back to normal mode
        t = new Timer() {
            @Override
            public void run() {
                started = false;
            }
        };
        t.schedule(getState().timeOut);
    }

    private void handleMouseUp(final Widget widget, MouseUpEvent event) {
        if (!started) {
            MouseEventDetails details = new MouseEventDetails();
            int button = event.getNativeButton();
            MouseButton mouseButton = null;
            if (button == NativeEvent.BUTTON_LEFT) {
                mouseButton = MouseButton.LEFT;
            }
            if (button == NativeEvent.BUTTON_MIDDLE) {
                mouseButton = MouseButton.MIDDLE;
            }
            if (button == NativeEvent.BUTTON_RIGHT) {
                mouseButton = MouseButton.RIGHT;
            }
            details.setButton(mouseButton);
            details.setClientX(event.getClientX());
            details.setClientY(event.getClientY());
            details.setRelativeX(event.getRelativeX(widget.getElement()));
            details.setRelativeY(event.getRelativeY(widget.getElement()));
            rpc.pressed(details);
        }
    }

    private void handleTouchEnd(final Widget widget, TouchEndEvent event) {
        if (!started) {
            rpc.pressed(null);
        }
    }

    @Override
    public LongPressExtensionState getState() {
        return (LongPressExtensionState) super.getState();
    }

}
