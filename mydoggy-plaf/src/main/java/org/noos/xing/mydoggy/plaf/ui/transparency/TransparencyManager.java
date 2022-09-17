package org.noos.xing.mydoggy.plaf.ui.transparency;

import java.awt.*;

/**
 * 
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public interface TransparencyManager<E extends Component> {

    /**
     * Returns <code>true</code> if the manager can manage the transparency, <code>false</code> otherwise. 
     *
     * @return <code>true</code> if the manager can manage the transparency, <code>false</code> otherwise.
     */
    boolean isServiceAvailable();

    /**
     * Sets the transparency value for the spicified component.
     *
     * @param component the component to which assign the transparency.
     * @param transparency the transparency value. The valid range is [0.0 , 1.0].
     */
    void setAlphaModeRatio(E component, float transparency);

    /**
     * Returns <code>true</code> if the transparency value of the <code>component</code> if higher than zero,
     * <code>false</code> otherwise.
     * @param component the component for which retrieve the transparency value.
     * @return <code>true</code> if the transparency value of the <code>component</code> if higher than zero,
     * <code>false</code> otherwise.
     */
    boolean isAlphaModeEnabled(E component);

}
