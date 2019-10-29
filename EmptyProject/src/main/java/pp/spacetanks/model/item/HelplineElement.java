package pp.spacetanks.model.item;

import pp.spacetanks.util.DoubleVec;
import pp.spacetanks.view.Visitor;

/**
 * A single element of the Helpline
 */
public class HelplineElement {
    public final DoubleVec position;

    /**
     * Creates a new HelplineElement
     * @param position  the position of the HelplineElement
     */
    public HelplineElement(DoubleVec position){this.position = position;}
    public void accept(Visitor v){v.visit(this);
    }
}
