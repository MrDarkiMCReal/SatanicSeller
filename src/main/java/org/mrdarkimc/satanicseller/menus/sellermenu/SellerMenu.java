package org.mrdarkimc.satanicseller.menus.sellermenu;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.mrdarkimc.SatanicLib.NotifyAPI.KeyedMessage;
import org.mrdarkimc.satanicmenus.buttons.MenuButton;
import org.mrdarkimc.satanicmenus.clickactions.MenuClickAction;
import org.mrdarkimc.satanicmenus.menus.AbstractMenu;
import org.mrdarkimc.satanicmenus.utils.BlockedSlots;
import org.mrdarkimc.satanicseller.SatanicSeller;

import java.util.Arrays;
import java.util.Map;

public class SellerMenu extends AbstractMenu implements Cloneable {
    private final MenuService service;
    public static SellerMenu menu;

    public SellerMenu(String name, int inventorySize, MenuService service) {
        super(name, inventorySize);
        this.service = service;
        menu = this;
        //setClickActions(Collections.nCopies(90,null),false);
        blockedSlots = Arrays.copyOf(BlockedSlots.getUpperInventoryLeftAndRightColumnBlock(), BlockedSlots.getUpperInventoryLeftAndRightColumnBlock().length);
        blockedSlots[46] = true;
        blockedSlots[47] = true;
        blockedSlots[48] = true;
        blockedSlots[49] = true;
        blockedSlots[50] = true;
        blockedSlots[51] = true;
        blockedSlots[52] = true;
        setButtonsSize(54);
        addButton(createPreviewButton(), 48,true);
    }
    private MenuButton createPreviewButton(){
        MenuClickAction action = (k,v) -> SatanicSeller.getInstance().getFactory().createPreview().openTo(k);
        ItemStack itemStack = new ItemStack(Material.EMERALD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(PlaceholderAPI .setPlaceholders(null,"%design_clr_main%Предпросмотр цен"));
        itemStack.setItemMeta(itemMeta);
        return new MenuButton(itemStack, action);
    }
    //    private static boolean[] blockedSlots;
//    static {
//        blockedSlots = Arrays.copyOf(BlockedSlots.getUpperInventoryLeftAndRightColumnBlock(),BlockedSlots.getUpperInventoryLeftAndRightColumnBlock().length);
//        blockedSlots[46] = true;
//        blockedSlots[47] = true;
//        blockedSlots[48] = true;
//        blockedSlots[49] = true;
//        blockedSlots[50] = true;
//        blockedSlots[51] = true;
//        blockedSlots[52] = true;
//    }
    @Override
    public void onOpen(Player player) {
        createHead(player);
    }

    private void createHead(Player player) {
        ItemStack stack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itemMeta = (SkullMeta) stack.getItemMeta();
        itemMeta.setCustomModelData(10007);
        itemMeta.setOwningPlayer(player);
        stack.setItemMeta(itemMeta);
        getInventory().setItem(44, stack);
    }

    @Override
    public void onClose(Player player) {
        int price = service.sellAndGetTotalPriceForItems(player, this);
        if (price != 0) {
            SatanicSeller.getDollarCurrency().addMoney(player, price);
            KeyedMessage.of("sellinv").withPlaceholders(Map.of("{price}", formatPrice(price))).send(player);
        }
    }

    public static String formatPrice(int price) {
        String priceStr = String.valueOf(price);
        StringBuilder result = new StringBuilder();
        int count = 0;

        for (int i = priceStr.length() - 1; i >= 0; i--) {
            result.append(priceStr.charAt(i));
            count++;

            if (count % 3 == 0 && i > 0) {
                result.append(",");
            }
        }

        return result.reverse().toString();
    }

    @Override
    public SellerMenu clone() {
        try {
            SellerMenu clone = (SellerMenu) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
