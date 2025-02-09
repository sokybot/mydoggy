package org.noos.xing.mydoggy;

import javax.swing.*;
import java.awt.*;

/**
 * This interface is the main entry point to manager MyDoggy. Using this interface
 * the user can register/unregister tool windows, groups. The user can get the
 * content manager instance and type descriptor templates.
 *
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 * @see ToolWindow
 * @see ToolWindowGroup
 * @see ContentManager
 * @see ToolWindowTypeDescriptor
 * @see ToolWindowManagerListener
 * @since 1.0.0
 */
public interface ToolWindowManager extends DockableManager<ToolWindow> {

    /**
     * Returns the instance of <code>ContentManager</code> that manages main window contents.
     *
     * @return an instance of <code>ContentManager</code>.
     * @see org.noos.xing.mydoggy.ContentManager
     * @since 1.0.0
     */
    ContentManager getContentManager();

    /**
     * Returns the type descriptor for this manager.
     *
     * @return type descrptor for this manager.
     * @since 1.2.0
     */
    ToolWindowManagerDescriptor getToolWindowManagerDescriptor();

    /**
     * Returns an instance of <code>PersistenceDelegate</code> relative to this manager.
     *
     * @return an instance of <code>PersistenceDelegate</code> relative to this manager.
     * @since 1.2.0
     */
    PersistenceDelegate getPersistenceDelegate();
    
    /**
     * Register a new tool window into this window manager based on the passed parameters.
     *
     * @param id        id of tool window to be registered.
     * @param title     title of tool window to be registered (can be null).
     * @param icon      icon of tool window to be registered (can be null).
     * @param component component which represents tool window content.
     * @param anchor    anchor of tool window to be registered.
     * @return the registered tool window.
     * @throws java.lang.IllegalArgumentException
     *          if exist a tool window registered
     *          with the same id or one or more of the parameters is null.
     * @see org.noos.xing.mydoggy.ToolWindowAnchor
     * @see org.noos.xing.mydoggy.ToolWindowManager
     * @see #unregisterToolWindow(String)
     * @since 1.0.0
     */
    ToolWindow registerToolWindow(String id, String title, Icon icon,
                                  Component component, ToolWindowAnchor anchor);

    /**
     * Removes the tool window for this id from this window manager if it is present.
     *
     * @param id id of tool window to be removed.
     * @throws java.lang.IllegalArgumentException
     *          - if tool window with specified id isn't registered.
     * @see #registerToolWindow(String,String,javax.swing.Icon,java.awt.Component,ToolWindowAnchor)
     * @see #unregisterAllToolWindow()
     * @since 1.0.0
     */
    void unregisterToolWindow(String id);

    /**
     * Removes all toolwindows from this window manager if there are any.
     * @since 1.0.0
     */
    void unregisterAllToolWindow();

    /**
     * Returns the toolWindow to which this manager maps the specified alias.  Returns
     * <code>null</code> if the manager contains no mapping for this alias.
     *
     * @param alias alias whose associated toolWindow is to be returned.
     * @return the toolWindow to which this manager maps the specified alias, or
     *	       <code>null</code> if the manager contains no mapping for this alias.
     * @since 1.2.0
     * @deprecated use getToolWindow(Object) instead.
     */
    ToolWindow getToolWindowByAlias(Object alias);

    /**
     * Returns the id of currently active tool window.
     *
     * @return <code>ID</code> of currently active tool window or <code>null</code> if there is no active tool window.
     * @since 1.0.0
     */
    Object getActiveToolWindowId();

    /**
     * Returns the tool window to which this manager maps the specified key (the key could be the id or
     * an alias). Returns <code>null</code> if the manager contains no mapping for this id.
     *
     * @param key the key could be the id or an alias
     * @return the tool window to which this manager maps the specified key. If there is no registered tool
     *         then the method returns <code>null</code>.
     * @since 1.0.0
     */
    ToolWindow getToolWindow(Object key);

    /**
     * Returns the tool window whose index is <code>index</code>.
     *
     * @param index tool window index.
     * @return the tool window whose index is <code>index</code>.
     * @since 1.0.0
     */
    ToolWindow getToolWindow(int index);

    /**
     * Returns an array of the toolwindows registered into this manager.
     *
     * @return an array of the toolwindows registered into this manager.
     *         If there is no tool registered then it returns an empty array.
     * @since 1.0.0
     */
    ToolWindow[] getToolWindows();

    /**
     * Returns an array of the toolwindows, registered into this manager,
     * with the specified anchor.
     *
     * @param anchor anchor which toolwindows are anchored.
     * @return an array of the toolwindows, registered into this manager,
     *         anchored on passed anchor. If there is no registered tool window anchored on that anchor
     *         then it returns an empty array.
     * @see org.noos.xing.mydoggy.ToolWindowAnchor
     * @since 1.0.0
     */
    ToolWindow[] getToolsByAnchor(ToolWindowAnchor anchor);

    /**
     * Returns a special group that contains all toolwindows registered in this manager.
     * @return a group that contains all toolwindows registered in this manager. 
     * @since 1.2.0
     */
    ToolWindowGroup getToolWindowGroup();

    /**
     * Returns the tool window group to which this manager maps the specified name.
     * If the manager contains no mapping for this name then the manager create a new instance
     * of ToolWindowGroup and associates the group created with the specified name in this manager.
     *
     * @param name name of tool window group.
     * @return the tool window group to which this manager maps the specified name.
     * @see org.noos.xing.mydoggy.ToolWindowGroup
     * @see #getToolWindowGroups()
     * @since 1.0.0
     */
    ToolWindowGroup getToolWindowGroup(String name);

    /**
     * Returns an array of the toolwindows groups registered into this manager.
     *
     * @return an array of the toolwindows groups registered into this manager.
     *         If there is no group registered then it returns an empty array.
     * @see #getToolWindowGroup(String)
     * @since 1.0.0
     */
    ToolWindowGroup[] getToolWindowGroups();

    /**
     * Removes the tool window group for this name from this manager if it is present.
     *
     * @param name name whose group is to be removed from the manager.
     * @return true if there exist a group for this name from this manager, false otherwise.
     * @see #getToolWindowGroup(String)
     * @since 1.0.0
     */
    boolean removeToolWindowGroup(String name);

    /**
     * Removes the tool window group from this manager if it is registered.
     *
     * @param toolWindowGroup the group to be removed from the manager.
     * @return true if the group is registered into this manager, false otherwise.
     * @see #getToolWindowGroup(String)
     * @since 1.3.1
     */
    boolean removeToolWindowGroup(ToolWindowGroup toolWindowGroup);

    /**
     * Returns <code>true</code> if this manager contains a group for the specified name.
     *
     * @param name name whose presence in this manager is to be tested.
     * @return <code>true</code> if this manager contains a group for the specified name.
     * @since 1.0.0
     */
    boolean containsGroup(String name);

    /**
     * Returns the dockable to which a dockable manager (this manager, the content manager or a toolwindow)
     * maps the specified id.
     * Returns <code>null</code> if the no manager contains a mapping for this id.
     *
     * @param key dockable's id or alias.
     * @return registered dockable with specified id. If there is no registered
     *         dockable with specified id then the method returns <code>null</code>.
     * @see org.noos.xing.mydoggy.Dockable
     * @since 1.5.0
     */
    Dockable lookupDockable(Object key);

    /**
     * Returns the instance of ToolWindowBar related to the specified anchor.
     *
     * @param anchor for which the related instance of ToolWindowBar is to be returned.
     * @return the instance of ToolWindowBar related to the specified anchor. 
     * @since 1.4.2
     */
    ToolWindowBar getToolWindowBar(ToolWindowAnchor anchor);

    /**
     * Returns the template type descrptor for <code>type</code>.
     * Any modifications to those templates will be reflected on all registered tool windows.
     *
     * @param type type whose template is to be returned from the manager.
     * @return the type descrptor for <code>type</code>.
     * @throws java.lang.IllegalArgumentException
     *          - if doen't exist a template for <code>type</code>.
     * @see ToolWindowType
     * @see org.noos.xing.mydoggy.ToolWindowTypeDescriptor
     * @since 1.0.0
     */
    ToolWindowTypeDescriptor getTypeDescriptorTemplate(ToolWindowType type);

    /**
     * Registers <code>listener</code> so that it will receive events when
     * the toolwindows and groups are registered or removed..
     * If listener <code>listener</code> is <code>null</code>,
     * no exception is thrown and no action is performed.
     *
     * @param listener the <code>ToolWindowManagerListener</code> to register.
     * @see ToolWindowManagerListener
     * @since 1.0.0
     */
    void addToolWindowManagerListener(ToolWindowManagerListener listener);

    /**
     * Unregisters <code>listener</code> so that it will no longer receive
     * events. This method performs no function, nor does it throw an exception, if the listener
     * specified by the argument was not previously added to this group.
     * If listener <code>listener</code> is <code>null</code>,
     * no exception is thrown and no action is performed.
     *
     * @param listener the <code>ToolWindowManagerListener</code> to be removed
     * @see #addToolWindowManagerListener(ToolWindowManagerListener)
     * @since 1.0.0
     */
    void removeToolWindowManagerListener(ToolWindowManagerListener listener);

    /**
     * Returns an array of all the tool window manager listeners
     * registered on this manager.
     *
     * @return all of the group's <code>ToolWindowManagerListener</code>s
     *         or an empty array if no tool window manager listeners are currently registered.
     * @see #addToolWindowManagerListener(ToolWindowManagerListener)
     * @see #removeToolWindowManagerListener(ToolWindowManagerListener)
     * @since 1.0.0
     */
    ToolWindowManagerListener[] getToolWindowManagerListeners();

}
