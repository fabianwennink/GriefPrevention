package me.ryanhamshire.GriefPrevention;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class WelcomeTask implements Runnable
{
    private Player player;
    
    public WelcomeTask(Player player) {
        this.player = player;
    }
    
    @Override
    public void run()
    {
        //abort if player has logged out since this task was scheduled
        if(!this.player.isOnline()) return;
        
        //offer advice and a helpful link
        GriefPrevention.sendMessage(player, TextMode.Instr, Messages.SurvivalBasicsVideo2, DataStore.SURVIVAL_VIDEO_URL);
        
        //give the player a reference book for later
        if(GriefPrevention.instance.config_claims_supplyPlayerManual)
        {
            ItemFactory factory = Bukkit.getItemFactory();
            BookMeta meta = (BookMeta) factory.getItemMeta(Material.WRITTEN_BOOK);

            DataStore datastore = GriefPrevention.instance.dataStore;
            meta.setAuthor(datastore.getMessage(Messages.BookAuthor));
            meta.setTitle(datastore.getMessage(Messages.BookTitle));
            
            StringBuilder page1 = new StringBuilder();
            String URL = colorize(datastore.getMessage(Messages.BookLink));
            String intro = colorize(datastore.getMessage(Messages.BookIntro));
            
            page1.append(URL).append("\n\n");
            page1.append(intro).append("\n\n");

            StringBuilder page2 = new StringBuilder();

            page2.append(colorize(datastore.getMessage(Messages.BookUsefulCommands))).append("\n\n");
            page2.append("/Trust\n");
            page2.append("/UnTrust\n");
            page2.append("/TrustList\n");
            page2.append("/ClaimsList\n\n");
            
            page2.append("/AbandonClaim\n");
            page2.append("/ExtendClaim\n");
            page2.append("/SubdivideClaims\n\n");
            
            page2.append("/AccessTrust\n");
            page2.append("/ContainerTrust\n");
            page2.append("/PermissionTrust");
            
            meta.setPages(page1.toString(), page2.toString());

            ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
            item.setItemMeta(meta);
            player.getInventory().addItem(item);
        } 
    }
    
    private String colorize(String str) {
    	return ChatColor.translateAlternateColorCodes('&', str);
    }
}
