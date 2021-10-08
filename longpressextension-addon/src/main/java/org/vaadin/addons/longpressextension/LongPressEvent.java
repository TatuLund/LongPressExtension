package org.vaadin.addons.longpressextension;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.CustomComponent;

/**
 * Long press event is emitted when user does long click with mouse or long
 * press with touch.
 * 
 * @author Tatu Lund
 */
public class LongPressEvent extends CustomComponent.Event {

    private MouseEventDetails details;

    public LongPressEvent(AbstractComponent source, MouseEventDetails details) {
        super(source);
        this.details = details;
    }

    /**
     * Return the mouse details if any.
     * 
     * @return The details, null if touch event.
     */
    public MouseEventDetails getDetails() {
        return details;
    }
}
