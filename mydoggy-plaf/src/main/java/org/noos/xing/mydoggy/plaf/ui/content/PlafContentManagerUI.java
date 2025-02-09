package org.noos.xing.mydoggy.plaf.ui.content;

import org.noos.xing.mydoggy.Content;
import org.noos.xing.mydoggy.ContentManagerUI;
import org.noos.xing.mydoggy.ToolWindowManager;

import javax.swing.*;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 * @since 1.3.1
 */
public interface PlafContentManagerUI {

    /**
     * Configures this manager appropriate for the specified toolwindow manager.
     * During this phase the new ContentManagerUI imports all properties the
     * old manager and initialize all needed parts. Furthermore all previous registered content
     * are added.
     *
     * @param manager the component where this UI delegate is being installed
     * @param oldContentManagerUI the old ContentManagerUI
     * @see #uninstall()
     * @since 1.3.1
     * @return this PlafContentManagerUI. 
     */
    PlafContentManagerUI install(ContentManagerUI oldContentManagerUI, ToolWindowManager manager);

    /**
     * Reverses configuration which was done during <code>install(...)</code>.
     *
     * @see #install
     * @since 1.3.1
     */
    void uninstall();

    /**
     * Returns <code>true</code> is the manager is installed, <code>false</code> otherwise.
     * 
     * @return <code>true</code> is the manager is installed, <code>false</code> otherwise.
     * @since 1.3.1
     */
    boolean isInstalled();

    /**
     * Adds the ui part of a content.
     *
     * @param content the content ui part to be added.
     * @param constraints one or more constraint used to add the ui part of the content.
     * @since 1.4.0
     */
    void addContent(PlafContent content, Object... constraints);

    /**
     * Removes the ui part of a content.
     *
     * @param content the content ui part to be removed.
     * @since 1.3.1
     */
    void removeContent(PlafContent content);

    /**
     * Returns whether or not the specified content is currently selected.
     *
     * @param content on which check the selection state.
     * @return true if the content is selected; false otherwise
     * @since 1.3.1
     */
    boolean isSelected(Content content);

    /**
     * Sets whether or not the content is selected.
     *
     * @param content on which change the selection state.
     * @param selected whether or not the content should be selected.
     * @since 1.3.1
     */
    void setSelected(Content content, boolean selected);

    /**
     * Sets the default popup menu for the contents.
     * If a content has no specific popup menu then the content manager will show
     * <code>popupMenu</code>.
     *
     * @param popupMenu the default popup menu for the contents.
     * @since 1.3.1
     */
    void setPopupMenu(JPopupMenu popupMenu);

    /**
     * Returns the default popup menu for the contents.
     *
     * @return the default <code>PopupMenu</code> for the contents.
     * @since 1.3.1
     */
    JPopupMenu getPopupMenu();

    /**
     * Calls the updateUI method on all components used by this manager.
     *
     * @since 1.3.1
     */
    void updateUI();

    /**
     *
     * @param content
     * @since 1.4.2
     */
    void selectNextContent(Content content);
}
