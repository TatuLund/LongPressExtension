package org.vaadin.addons.tatu.longpressextension.client;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.communication.ServerRpc;

public interface LongPressExtensionServerRpc extends ServerRpc {

    public void pressed(MouseEventDetails mouseDetails);

}
