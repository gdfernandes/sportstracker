package de.saring.sportstracker.gui.dialogsfx;

import de.saring.sportstracker.data.Equipment;
import de.saring.sportstracker.data.SportSubType;
import de.saring.sportstracker.data.SportType;
import de.saring.util.gui.javafx.ColorConverter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

/**
 * This ViewModel class provides JavaFX properties of all SportType attributes to be edited in the SportType dialog.
 * So they can be bound to the appropriate dialog view controls.
 *
 * @author Stefan Saring
 */
public class SportTypeViewModel {

    public final int id;
    public final StringProperty name;
    public final BooleanProperty recordDistance;
    public final StringProperty icon;
    public final ObjectProperty<Color> color;
    public final ObservableList<SportSubType> sportSubTypes;
    public final ObservableList<Equipment> equipments;

    /**
     * Creates the SportTypeViewModel with JavaFX properties for the passed SportType object.
     *
     * @param sportType SportType to be edited
     */
    public SportTypeViewModel(final SportType sportType) {
        this.id = sportType.getId();
        this.name = new SimpleStringProperty(sportType.getName());
        this.recordDistance = new SimpleBooleanProperty(sportType.isRecordDistance());
        this.icon = new SimpleStringProperty(sportType.getIcon());
        this.color = new SimpleObjectProperty<>(sportType.getColor() == null ?
                Color.BLACK : ColorConverter.toFxColor(sportType.getColor()));

        this.sportSubTypes = FXCollections.observableArrayList();
        sportType.getSportSubTypeList().forEach(sportSubType -> sportSubTypes.add(sportSubType));

        this.equipments = FXCollections.observableArrayList();
        sportType.getEquipmentList().forEach(equipment -> equipments.add(equipment));
    }

    /**
     * Creates a new SportType domain object from the edited JavaFX properties.
     *
     * @return SportType
     */
    public SportType getSportType() {
        final SportType sportType = new SportType(id);
        sportType.setName(name.getValue().trim());
        sportType.setRecordDistance(recordDistance.getValue());
        sportType.setIcon(icon.getValue().trim());
        sportType.setColor(ColorConverter.toAwtColor(color.getValue()));

        sportSubTypes.forEach(sportSubType -> sportType.getSportSubTypeList().set(sportSubType));
        equipments.forEach(equipment -> sportType.getEquipmentList().set(equipment));
        return sportType;
    }
}