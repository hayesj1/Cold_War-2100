package resource;

import java.util.ListResourceBundle;

/**
 * Created by hayesj3 on 7/8/2015.
 */
public class TexualResources extends ListResourceBundle {
    private static TexualResources instance = new TexualResources();
    /**
     * A 2 Dimensionsal array to hold contents. Access like so: [Index_of_Pair].getValue()
     * <p>
     * Family indices:<br>
     *     0=Buttons,
     *     1=Labels,
     *     2=Names,
     *     3=Descriptions,
     *     4=Messages
     * </p>
     * @see resource.TexualResources.Pair
     */
    private Object[][] contents = {

                    {
                            // LOCALIZE THIS
                            new Pair<String, Object>("okKey", "Okay")
                    }
    };

    public static TexualResources getInstance() {
        return instance;
    }

    private TexualResources() {}

    @Override
    protected Object[][] getContents() {
        //ResourceBundle
        return contents.clone();
    }
    protected class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public K getKey() { return this.key; }
        public V getValue() { return this.value;}
    }
}
