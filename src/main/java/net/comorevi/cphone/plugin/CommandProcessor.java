package net.comorevi.cphone.plugin;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.utils.TextFormat;
import net.comorevi.cphone.cphone.data.RuntimeData;
import net.comorevi.cphone.presenter.SharingData;

class CommandProcessor {

    protected static boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (command.getName()) {
            case "setHomeMessage": {
                if (sender instanceof Player) {
                    if (((Player) sender).isOp()) {
                        sender.sendMessage(TextFormat.RED + "opのみがこのコマンドを使えます。");
                        return true;
                    }
                }

                if (args.length < 1) {
                    sender.sendMessage(TextFormat.RED + "メッセージを指定してください。");
                    return true;
                }

                SharingData.homeText = args[0];
                RuntimeData.config.set("HomeText", args[0]);
                RuntimeData.config.save();

                return true;
            }
        }
        return true;
    }

}
