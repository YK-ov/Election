import java.awt.*;
import java.util.List;

public class SelectableMap extends VoivodeshipMap{

    @Override
    public List<String> getStates() {
        return super.getStates();
    }

    public String select(String stateName){
        return stateName;
    }

    @Override
    protected Color setColor(String stateName) {
        for (int i = 0; i < getStates().size(); i++) {
            if (stateName.equals(getStates().getLast())) {
                System.out.println(getStates().get(i) + " last woje?");
                return Color.RED;
            }
        }

        return super.setColor(stateName);
    }
}
