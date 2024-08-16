package me.emmy.hub.feature.cosmetic.menu;

import lombok.AllArgsConstructor;
import me.emmy.hub.Forest;
import me.emmy.hub.feature.cosmetic.Cosmetic;
import me.emmy.hub.feature.cosmetic.CosmeticRepository;
import me.emmy.hub.utils.CC;
import me.emmy.hub.utils.ItemBuilder;
import me.emmy.hub.utils.menu.Button;
import me.emmy.hub.utils.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Emmy
 * @project Forest
 * @date 16/08/2024 - 16:48
 */
public class CosmeticsMenu extends Menu {
    @Override
    public String getTitle(Player player) {
        return CC.translate("Cosmetics");
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        final Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(4, new UnEquipCosmeticButton());

        addBorder(buttons, (byte) 15, 5 );

        int slot = 10;

        List<Cosmetic> sortedCosmetics = Forest.getInstance()
                .getCosmeticRepository()
                .getCosmetics()
                .stream()
                .sorted(Comparator.comparing(Cosmetic::getName))
                .sorted(Comparator.comparing(Cosmetic::getType))
                .collect(Collectors.toList());

        for (Cosmetic cosmetic : sortedCosmetics) {
            buttons.put(slot++, new CosmeticsButton(cosmetic));
            if (slot == 17 || slot == 26 || slot == 35 || slot == 44) {
                slot++;
                slot++;
            }
        }

        return buttons;
    }

    @Override
    public int getSize() {
        return 5 * 9;
    }

    @AllArgsConstructor
    private static class CosmeticsButton extends Button {

        private final Cosmetic cosmetic;

        @Override
        public ItemStack getButtonItem(Player player) {
            CosmeticRepository cosmeticRepository = Forest.getInstance().getCosmeticRepository();
            List<String> lore = Arrays.asList(
                    "",
                    CC.translate("&dDescription:"),
                    CC.translate("&f" + cosmetic.getDescription()),
                    "",
                    CC.translate("&dType:"),
                    CC.translate("&f" + cosmetic.getType().getName()),
                    "",
                    //CC.translate("&dPermission:"),
                    //CC.translate("&f" + cosmetic.getPermission()),
                    //"",
                    player.hasPermission(cosmetic.getPermission()) ? cosmeticRepository.isEquipped(player, cosmetic) ? CC.translate("&a&lEquipped") : CC.translate("&c&lClick to equip") : CC.translate("&c&lYou don't have permission to equip this Cosmetic!")
            );
            return new ItemBuilder(cosmetic.getMaterial())
                    .name(CC.translate("&d&l" + cosmetic.getName()))
                    .lore(lore)
                    .build();
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            if (clickType != ClickType.LEFT) return;

            if (!player.hasPermission(cosmetic.getPermission())) {
                player.sendMessage(CC.translate("&cYou don't have permission to equip this Cosmetic!"));
                return;
            }

            if (Forest.getInstance().getCosmeticRepository().isEquipped(player, cosmetic)) {
                player.sendMessage(CC.translate("&cYou've already equipped this Cosmetic!"));
                return;
            }

            CosmeticRepository cosmeticRepository = Forest.getInstance().getCosmeticRepository();
            cosmeticRepository.equipCosmetic(player, cosmetic);
            player.sendMessage(CC.translate("&dYou've equipped the &f" + cosmetic.getName() + "&d cosmetic!"));
            player.closeInventory();
        }
    }

    @AllArgsConstructor
    private static class UnEquipCosmeticButton extends Button {

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.INK_SACK)
                    .name(CC.translate("&c&lUnequip Cosmetic"))
                    .lore(CC.translate("&cClick to unequip your current cosmetic!"))
                    .durability(1)
                    .build();
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            if (clickType != ClickType.LEFT) return;

            if (Forest.getInstance().getCosmeticRepository().hasEquipped(player)) {
                Forest.getInstance().getCosmeticRepository().unEquipCosmetic(player);
                player.sendMessage(CC.translate("&cYou've unequipped your current cosmetic!"));
                player.closeInventory();
                return;
            }

            player.sendMessage(CC.translate("&cYou don't have any cosmetics equipped!"));
        }
    }
}
