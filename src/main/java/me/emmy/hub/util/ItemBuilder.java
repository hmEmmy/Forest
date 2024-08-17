package me.emmy.hub.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder implements Listener {

	private final ItemStack itemStack;
	private String command;
	private boolean commandEnabled = true;

	/**
	 * Create a new ItemBuilder with a material.
	 *
	 * @param mat The material to create the item with.
	 */
	public ItemBuilder(Material mat) {
		itemStack = new ItemStack(mat);
	}

	/**
	 * Create a new ItemBuilder with an existing ItemStack.
	 *
	 * @param itemStack The ItemStack to create the ItemBuilder with.
	 */
	public ItemBuilder(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	/**
	 * Set the amount of the item.
	 *
	 * @param amount The amount to set the item to.
	 * @return The ItemBuilder instance.
	 */
	public ItemBuilder amount(int amount) {
		itemStack.setAmount(amount);
		return this;
	}

	/**
	 * Set the name of the item.
	 *
	 * @param name The name to set the item to.
	 * @return The ItemBuilder instance.
	 */
	public ItemBuilder name(String name) {
		ItemMeta meta = itemStack.getItemMeta();

		// Check if meta is null and create a new one if needed
		if (meta == null) {
			meta = Bukkit.getItemFactory().getItemMeta(itemStack.getType());
		}

		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		itemStack.setItemMeta(meta);

		return this;
	}

	/**
	 * Add a line of lore to the item.
	 *
	 * @param name The lore to add to the item.
	 * @return The ItemBuilder instance.
	 */
	public ItemBuilder lore(String name) {
		ItemMeta meta = itemStack.getItemMeta();
		List<String> lore = meta.getLore();

		if (lore == null) {
			lore = new ArrayList<>();
		}

		lore.add(ChatColor.translateAlternateColorCodes('&', name));
		meta.setLore(lore);

		itemStack.setItemMeta(meta);

		return this;
	}

	/**
	 * Add multiple lines of lore to the item.
	 *
	 * @param lore The lore to add to the item.
	 * @return The ItemBuilder instance.
	 */
	public ItemBuilder lore(String... lore) {
		List<String> toSet = new ArrayList<>();
		ItemMeta meta = itemStack.getItemMeta();

		for (String string : lore) {
			toSet.add(ChatColor.translateAlternateColorCodes('&', string));
		}

		meta.setLore(toSet);
		itemStack.setItemMeta(meta);

		return this;
	}

	/**
	 * Add multiple lines of lore to the item.
	 *
	 * @param lore The lore to add to the item.
	 * @return The ItemBuilder instance.
	 */
	public ItemBuilder lore(List<String> lore) {
		List<String> toSet = new ArrayList<>();
		ItemMeta meta = itemStack.getItemMeta();

		for (String string : lore) {
			toSet.add(ChatColor.translateAlternateColorCodes('&', string));
		}

		meta.setLore(toSet);
		itemStack.setItemMeta(meta);

		return this;
	}

	/**
	 * Set the durability of the item.
	 *
	 * @param durability The durability to set the item to.
	 * @return The ItemBuilder instance.
	 */
	public ItemBuilder durability(int durability) {
		itemStack.setDurability((short) durability);
		return this;
	}

	/**
	 * Add an enchantment to the item.
	 *
	 * @param enchantment The enchantment to add to the item.
	 * @param level       The level of the enchantment.
	 * @return The ItemBuilder instance.
	 */
	public ItemBuilder enchantment(Enchantment enchantment, int level) {
		itemStack.addUnsafeEnchantment(enchantment, level);
		return this;
	}

	/**
	 * Add an enchantment to the item.
	 *
	 * @param enchantment The enchantment to add to the item.
	 * @return The ItemBuilder instance.
	 */
	public ItemBuilder enchantment(Enchantment enchantment) {
		itemStack.addUnsafeEnchantment(enchantment, 1);
		return this;
	}

	/**
	 * Set the type of the item.
	 *
	 * @param material The material to set the item to.
	 * @return The ItemBuilder instance.
	 */
	public ItemBuilder type(Material material) {
		itemStack.setType(material);
		return this;
	}

	/**
	 * Clear the lore of the item.
	 *
	 * @return The ItemBuilder instance.
	 */
	public ItemBuilder clearLore() {
		ItemMeta meta = itemStack.getItemMeta();

		meta.setLore(new ArrayList<>());
		itemStack.setItemMeta(meta);

		return this;
	}

	/**
	 * Clear the enchantments of the item.
	 *
	 * @return The ItemBuilder instance.
	 */
	public ItemBuilder clearEnchantments() {
		for (Enchantment e : itemStack.getEnchantments().keySet()) {
			itemStack.removeEnchantment(e);
		}

		return this;
	}

	/**
	 * Hide the meta of the item.
	 *
	 * @return The ItemBuilder instance.
	 */
	public ItemBuilder hideMeta() {
		ItemMeta meta = itemStack.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemStack.setItemMeta(meta);
		return this;
	}

	/**
	 * Add a command to the item.
	 *
	 * @param command The command to add to the item.
	 * @return The ItemBuilder instance.
	 */
	public ItemBuilder command(String command) {
		this.command = command;
		return this;
	}

	/**
	 * Enable or disable the command on the item.
	 *
	 * @param enabled The boolean to enable or disable the command.
	 * @return The ItemBuilder instance.
	 */
	public ItemBuilder commandEnabled(boolean enabled) {
		this.commandEnabled = enabled;
		return this;
	}

	/**
	 * Build the item.
	 *
	 * @return The built ItemStack.
	 */
	public ItemStack build() {
		ItemMeta meta = itemStack.getItemMeta();

		if (commandEnabled && command != null) {
			List<String> lore = meta.getLore();

			if (lore == null) {
				lore = new ArrayList<>();
			}

			lore.add("command:" + command);
			meta.setLore(lore);
		}

		itemStack.setItemMeta(meta);
		return itemStack;
	}
}