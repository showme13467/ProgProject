package pp.spacetanks.view;

public enum FieldSize {
    SMALL(0),
    MIDDLE(1),
    LARGE(2);

    /**
     * Gets the factor for setting the canvas size.
     * @param val factor
     * @return  The factor depending on the current fieldSize.
     */
    FieldSize(int val){
        this.val = val;
    }
    public final int val;
}
