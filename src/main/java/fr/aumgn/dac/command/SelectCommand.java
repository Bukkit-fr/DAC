package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.config.DACArea;
import fr.aumgn.dac.config.DACArea.InvalidRegionType;
import fr.aumgn.dac.config.DACArena;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class SelectCommand extends PlayerCommandExecutor {
	
	private DAC plugin;
	
	public SelectCommand(DAC plugin) {
		this.plugin = plugin; 
	}

	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 2) { return false; }
		DACArena arena = plugin.getDACConfig().get(args[0]);
		if (arena == null) {
			context.error("Arène inconnu.");
			return true;
		}
		DACArea area;
		String areaName;
		if (args[1].equalsIgnoreCase("pool")) {
			area = arena.getPool();
			areaName = "La zone du bassin";
		} else if (args[1].equalsIgnoreCase("start")) {
			area = arena.getStartArea();
			areaName = "La zone de départ";
		} else {
			return false;
		}
		try {
			plugin.getWorldEdit().setSelection(context.getPlayer(), area.getSelection());
			context.success(areaName + " a été sélectionné");
			return true;
		} catch (InvalidRegionType exc) {
			context.error("Une erreur est survenu durant la selection de la zone.");
			context.error(exc.getMessage());
			return true;
		}
	}
	
}
