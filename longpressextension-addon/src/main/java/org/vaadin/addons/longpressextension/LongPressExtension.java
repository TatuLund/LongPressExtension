package org.vaadin.addons.longpressextension;

import java.lang.reflect.Method;

import org.vaadin.addons.longpressextension.client.LongPressExtensionServerRpc;
import org.vaadin.addons.longpressextension.client.LongPressExtensionState;

import com.vaadin.event.ConnectorEventListener;
import com.vaadin.server.AbstractExtension;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.Registration;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.util.ReflectTools;

public class LongPressExtension extends AbstractExtension {

    public interface LongPressEventListener extends ConnectorEventListener {
        Method STATUSCHANGED_METHOD = ReflectTools.findMethod(
                LongPressEventListener.class, "pressed", LongPressEvent.class);

        public void pressed(LongPressEvent event);
    }

    private AbstractComponent component;

    public LongPressExtension(AbstractComponent component) {
        this.extend(component);
        this.component = component;

        LongPressExtensionServerRpc rpc = this::pressed;
        registerRpc(rpc);
    }

    @Override
    protected LongPressExtensionState getState() {
        return (LongPressExtensionState) super.getState();
    }

    private void pressed(MouseEventDetails mouseDetails) {
        fireEvent(new LongPressEvent(component, mouseDetails));
    }

    /**
     * Sets the timeout to determine what is a long gesture. The default is 2
     * seconds.
     * 
     * @param timeOut
     *            Time out in milliseconds
     */
    public void setTimeout(int timeOut) {
        getState().timeOut = timeOut;
    }

    /**
     * Add a new LongPressEventListener The LongPressEvent when user does long
     * click and hold or touch gesture on the UI component.
     * 
     * @param listener
     *            A LongPressEventListener to be added
     */
    public Registration addLongPressListener(LongPressEventListener listener) {
        return addListener(LongPressEvent.class, listener,
                LongPressEventListener.STATUSCHANGED_METHOD);
    }
}
