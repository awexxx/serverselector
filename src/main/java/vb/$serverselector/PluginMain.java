package vb.$serverselector;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.plugin.java.*;
import org.bukkit.util.*;
import com.gmail.visualbukkit.stdlib.*;

public class PluginMain extends JavaPlugin implements Listener {

	private static PluginMain instance;
	private static Object localVariableScope = new Object();

	public void onEnable() {
		instance = this;
		getDataFolder().mkdir();
		getServer().getPluginManager().registerEvents(this, this);
		Object localVariableScope = new Object();
		try {
			Bukkit.getConsoleSender().sendMessage(PluginMain
					.color("&cPCN Server Selector &b>> The PCN Server Selector has loaded! Made with <3 by Awex"));
			new org.bukkit.configuration.file.YamlConfiguration().save(new File("config.yml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		getServer().getPluginManager().registerEvents(GUIManager.getInstance(), this);
		GUIManager.getInstance().register("1", guiPlayer -> {
			try {
				Inventory gui = Bukkit.createInventory(new GUIIdentifier("1"), ((int) 9d),
						PluginMain.color("&cServer Selection"));
				gui.setItem(((int) 0d), new ItemStack(org.bukkit.Material.BLACK_STAINED_GLASS_PANE));
				gui.setItem(((int) 1d), PluginMain.getNamedItem(org.bukkit.Material.CRAFTING_TABLE, "&cLobby"));
				gui.setItem(((int) 2d), new ItemStack(org.bukkit.Material.BLACK_STAINED_GLASS_PANE));
				gui.setItem(((int) 3d), PluginMain.getNamedItem(org.bukkit.Material.DIAMOND_SWORD, "&cUHC"));
				gui.setItem(((int) 4d), new ItemStack(org.bukkit.Material.BLACK_STAINED_GLASS_PANE));
				gui.setItem(((int) 5d), PluginMain.getNamedItem(org.bukkit.Material.APPLE, "&cSMP"));
				gui.setItem(((int) 6d), new ItemStack(org.bukkit.Material.BLACK_STAINED_GLASS_PANE));
				gui.setItem(((int) 7d), PluginMain.getNamedItem(org.bukkit.Material.ENDER_EYE, "&cSkyWars"));
				gui.setItem(((int) 8d), new ItemStack(org.bukkit.Material.BLACK_STAINED_GLASS_PANE));
				return gui;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});
	}

	public void onDisable() {
	}

	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] commandArgs) {
		if (command.getName().equalsIgnoreCase("reload")) {
			try {
				Object localVariableScope = new Object();
				PluginMain.getInstance().reloadConfig();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (command.getName().equalsIgnoreCase("menu")) {
			try {
				Object localVariableScope = new Object();
				GUIManager.getInstance().open("1", ((org.bukkit.entity.Player) commandSender));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public static void procedure(String procedure, List<?> procedureArgs) throws Exception {
	}

	public static Object function(String function, List<?> functionArgs) throws Exception {
		return null;
	}

	public static List<Object> createList(Object obj) {
		List<Object> list = new ArrayList<>();
		if (obj.getClass().isArray()) {
			int length = java.lang.reflect.Array.getLength(obj);
			for (int i = 0; i < length; i++) {
				list.add(java.lang.reflect.Array.get(obj, i));
			}
		} else if (obj instanceof Collection<?>) {
			list.addAll((Collection<?>) obj);
		} else {
			list.add(obj);
		}
		return list;
	}

	public static String color(String string) {
		return string != null ? ChatColor.translateAlternateColorCodes('&', string) : null;
	}

	public static void createResourceFile(String path) {
		Path file = getInstance().getDataFolder().toPath().resolve(path);
		if (Files.notExists(file)) {
			try (InputStream inputStream = PluginMain.class.getResourceAsStream("/" + path)) {
				Files.createDirectories(file.getParent());
				Files.copy(inputStream, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static PluginMain getInstance() {
		return instance;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onGUIClickEvent1(com.gmail.visualbukkit.stdlib.GUIClickEvent event) throws Exception {
		if (PluginMain.checkEquals(new java.lang.Integer(event.getInventoryClickEvent().getSlot()),
				new java.lang.Double(1d))) {
			((Player) event.getInventoryClickEvent().getWhoClicked()).performCommand("server lobby");
			PluginMain.setItemLore(new ItemStack(org.bukkit.Material.CRAFTING_TABLE),
					PluginMain.createList("Lobby!".split("(?!^)")));
		}
		if (PluginMain.checkEquals(new java.lang.Integer(event.getInventoryClickEvent().getSlot()),
				new java.lang.Double(3d))) {
			((Player) event.getInventoryClickEvent().getWhoClicked()).performCommand("server uhc");
		}
		if (PluginMain.checkEquals(new java.lang.Integer(event.getInventoryClickEvent().getSlot()),
				new java.lang.Double(5d))) {
			((Player) event.getInventoryClickEvent().getWhoClicked()).performCommand("server smp");
			PluginMain.setItemLore(new ItemStack(org.bukkit.Material.APPLE),
					PluginMain.createList("SMP!".split("(?!^)")));
		}
		if (PluginMain.checkEquals(new java.lang.Integer(event.getInventoryClickEvent().getSlot()),
				new java.lang.Double(7d))) {
			((Player) event.getInventoryClickEvent().getWhoClicked()).performCommand("server skywars");
		}
	}

	public static ItemStack getNamedItem(Material material, String name) {
		ItemStack item = new ItemStack(material);
		ItemMeta itemMeta = item.getItemMeta();
		if (itemMeta != null) {
			itemMeta.setDisplayName(PluginMain.color(name));
			item.setItemMeta(itemMeta);
		}
		return item;
	}

	public static boolean checkEquals(Object o1, Object o2) {
		if (o1 == null || o2 == null) {
			return false;
		}
		return o1 instanceof Number && o2 instanceof Number
				? ((Number) o1).doubleValue() == ((Number) o2).doubleValue()
				: o1.equals(o2);
	}

	public static void setItemLore(ItemStack item, List lore) {
		ItemMeta itemMeta = item.getItemMeta();
		if (itemMeta != null) {
			List<String> coloredLore = new ArrayList<>(lore.size());
			for (Object obj : lore) {
				coloredLore.add(PluginMain.color((String) obj));
			}
			itemMeta.setLore(coloredLore);
			item.setItemMeta(itemMeta);
		}
	}
}
