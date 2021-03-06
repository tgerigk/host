package de.x8bit.Fantasya.Atlantis.Effects;

import de.x8bit.Fantasya.Atlantis.Building;
import de.x8bit.Fantasya.Atlantis.Effect;
import de.x8bit.Fantasya.Atlantis.Item;
import de.x8bit.Fantasya.Atlantis.Unit;
import de.x8bit.Fantasya.Atlantis.Buildings.Werkstatt;
import de.x8bit.Fantasya.Atlantis.Effect.EFXProduction;
import de.x8bit.Fantasya.Atlantis.Messages.Magie;

public class EFXWagnerMeister extends Effect implements EFXProduction {

	/** Konstruktor für Reflection */
	public EFXWagnerMeister() { super(); }
	
	public EFXWagnerMeister(Unit unit)
	{
		super(unit);	// Nummer holen
	}
	
	@Override
	public int EFXCalculate(Unit unit, Item item, int anzahl) {
		if (unit.getGebaeude() == 0) return 0; // in keinem Gebäude

		// Gebäude überprüfen - da nur Schmiede möglich
		Building building = Building.getBuilding(unit.getGebaeude());
		if (!building.getClass().equals(Werkstatt.class)) return 0; // in keiner Werkstatt
		
		int free = getIntegerProperty("anzahl", 10);	// 10 für Stufe 1 gezaubert
		if (free <= 0) { destroyIt(); /* Effekt zerstören */ return 0; }
		
		// Effekt berechnen
		int extra = (int) ((double) anzahl * 0.2);	// 20% extra
		if (extra > free) extra = free;						// Grenze checken
		int rest = free - extra;
		setProperty("anzahl", rest);
		
		// Meldung
		new Magie(unit + " produziert durch seine Erfahrung in der Werkstatt " + extra + " " + item.getName() + " mehr", unit);
		
		return extra;
	}

}
