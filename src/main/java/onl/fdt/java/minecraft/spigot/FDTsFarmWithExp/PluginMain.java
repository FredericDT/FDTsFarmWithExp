package onl.fdt.java.minecraft.spigot.FDTsFarmWithExp;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BlockIterator;

import java.util.Arrays;
import java.util.List;

public class PluginMain extends JavaPlugin implements Listener {

    private static final int defaultExpAmount = 3;
    private static final int registerBlockTypeRange = 10;
    private static final List<String> defaultBlockList = Arrays.asList("minecraft:carrots[age=7]", "minecraft:potatoes[age=7]", "minecraft:wheat[age=7]", "minecraft:beetroots[age=3]");

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.reloadConfig();
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        this.saveConfig();
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        if (this.getConfig().getList("BlockList", defaultBlockList).contains(event.getBlock().getBlockData().getAsString())) {
            event.setExpToDrop(this.getConfig().getInt("ExpAmoount", defaultExpAmount));
        }
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!"fdtsfarmwithexp".equalsIgnoreCase(command.getName())) {
            return null;
        }
        return Arrays.asList("setExpAmount", "registerExpBlock", "unregisterExpBlock");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!"fdtsfarmwithexp".equalsIgnoreCase(command.getName())) {
            return false;
        }

        if (args.length < 1) {
            sender.sendMessage(command.getUsage());
            return true;
        }

        if ("setExpAmount".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("fdtsfarmwithexp.setexpamount")) {
                sender.sendMessage("You don't have permission");
                return true;
            }
            if (args.length < 2) {
                sender.sendMessage(command.getUsage());
                return true;
            } else {
                int newAmount = Integer.valueOf(args[1]);
                this.getConfig().set("ExpAmount", newAmount);
                sender.sendMessage("ExpAmount has been set to " + newAmount);
                return true;
            }
        }

        if ("registerExpBlock".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("fdtsfarmwithexp.registerexpblock")) {
                sender.sendMessage("You don't have permission");
                return true;
            }
            if (!(sender instanceof Player)) {
                sender.sendMessage("Must be performed by a player");
                return true;
            } else {
                Player p = (Player) sender;
                Block b = getTargetBlock(p, registerBlockTypeRange);
                if (b.getType() != Material.AIR) {
                    List<String> l = this.getConfig().getStringList("BlockList");
                    if (!l.contains(b.getBlockData().getAsString())) {
                        String pendingBlockData = b.getBlockData().getAsString();
                        l.add(pendingBlockData);
                        this.getConfig().set("BlockList", l);
                        sender.sendMessage("Registered " + pendingBlockData);
                        return true;
                    }
                }
                return true;
            }
        }

        if ("unregisterExpBlock".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("fdtsfarmwithexp.unregisterexpblock")) {
                sender.sendMessage("You don't have permission");
                return true;
            }
            if (!(sender instanceof Player)) {
                sender.sendMessage("Must be performed by a player");
                return true;
            } else {
                Player p = (Player) sender;
                Block b = getTargetBlock(p, registerBlockTypeRange);
                if (b.getType() != Material.AIR) {
                    List<String> l = this.getConfig().getStringList("BlockList");
                    if (l.contains(b.getBlockData().getAsString())) {
                        String pendingBlockData = b.getBlockData().getAsString();
                        l.remove(pendingBlockData);
                        this.getConfig().set("BlockList", l);
                        sender.sendMessage("Unregistered " + pendingBlockData);
                        return true;
                    }
                }
                return true;
            }
        }

        return true;
    }

    private Block getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock;
    }
}
