package net.comorevi.cphone.plugin;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.event.server.DataPacketSendEvent;
import cn.nukkit.item.ItemGhastTear;
import cn.nukkit.network.protocol.ModalFormRequestPacket;
import cn.nukkit.network.protocol.ModalFormResponsePacket;
import cn.nukkit.network.protocol.NetworkStackLatencyPacket;
import cn.nukkit.scheduler.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.comorevi.cphone.cphone.CPhone;
import net.comorevi.cphone.cphone.application.ApplicationPermission;
import net.comorevi.cphone.cphone.data.RuntimeData;
import net.comorevi.cphone.cphone.model.CustomResponse;
import net.comorevi.cphone.cphone.model.ListResponse;
import net.comorevi.cphone.cphone.model.ModalResponse;
import net.comorevi.cphone.cphone.sql.ApplicationSQLManager;
import net.comorevi.cphone.cphone.widget.activity.ActivityBase;
import net.comorevi.cphone.cphone.widget.activity.base.CustomActivity;
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity;
import net.comorevi.cphone.cphone.widget.activity.base.ModalActivity;
import net.comorevi.cphone.cphone.widget.element.Dropdown;
import net.comorevi.cphone.cphone.widget.element.Element;
import net.comorevi.cphone.presenter.ActivityProcessor;
import net.comorevi.cphone.presenter.SharingData;

import java.util.ArrayList;
import java.util.List;

import static net.comorevi.cphone.presenter.SharingData.activities;

class EventListener implements Listener {

    @EventHandler
    public void onDataPacket(DataPacketReceiveEvent event) {
        if (event.getPacket() instanceof ModalFormResponsePacket) {
            if(!(event.getPacket() instanceof ModalFormResponsePacket)) return;

            ModalFormResponsePacket packet = (ModalFormResponsePacket) event.getPacket();
            int formId = packet.formId;
            ActivityBase activity = activities.get(formId);
            Player player = event.getPlayer();

            if (packet.data != null && !packet.data.equals("") && !packet.data.equals("null") && activity != null) {
                if (activity instanceof ListActivity) {
                    ListActivity simpleForm = (ListActivity) activity;

                    try {
                        int buttonIndex = Integer.parseInt(packet.data.replaceAll("[^0-9]", ""));
                        ActivityProcessor.stop(player, activity, new ListResponse(player, buttonIndex));
                        simpleForm.getButtons().get(buttonIndex).onClick(player);

                    } catch (NumberFormatException e1) {
                        ActivityProcessor.stop(player, activity, new ListResponse(player, -1));
                    }

                } else if (activity instanceof ModalActivity) {
                    String result = packet.data;
                    ActivityProcessor.stop(player, activity, new ModalResponse(player, result.contains("true") ? 1 : 2));

                } else if (activity instanceof CustomActivity) {
                    CustomActivity customForm = (CustomActivity) activity;
                    List<Object> list = new Gson().fromJson(packet.data, new TypeToken<List>(){}.getType());

                    List<Object> result = new ArrayList<>();
                    int count = 0;

                    if (list != null && customForm.getElements().size() == list.size()) {
                        for (Element element : customForm.getElements()) {
                            switch (element.getType()) {
                                case "string":
                                    result.add(String.valueOf(list.get(count)));
                                    break;

                                case "int":
                                    result.add(Integer.parseInt(String.valueOf(list.get(count))));
                                    break;

                                case "float":
                                    if (element instanceof Dropdown) {
                                        Dropdown dropdown = (Dropdown) element;
                                        result.add(dropdown.getOptions().get(Math.round(Float.parseFloat(String.valueOf(list.get(count))))));
                                    } else {
                                        result.add(Float.parseFloat(String.valueOf(list.get(count))));
                                    }
                                    break;

                                case "boolean":
                                    result.add(Boolean.parseBoolean(String.valueOf(list.get(count))));
                                    break;

                                case "null":
                                    result.add("null");
                                    break;
                            }
                            count++;
                        }
                    }

                    ActivityProcessor.stop(player, activity, new CustomResponse(player, result));
                }

                activities.remove(formId);
            }

        } else if (event.getPacket() instanceof NetworkStackLatencyPacket) {
            SharingData.server.getScheduler().scheduleDelayedTask(new Task() {
                @Override
                public void onRun(int i) {
                    event.getPlayer().sendAttributes();
                    System.out.println("SEND1");
                }
            }, 1);
        }
    }

    @EventHandler
    public void onSendPacket(DataPacketSendEvent event) {
        if (event.getPacket() instanceof ModalFormRequestPacket) {
            ModalFormRequestPacket pk = new ModalFormRequestPacket();
            Player player = event.getPlayer();
            SharingData.server.getScheduler().scheduleDelayedTask(new Task() {
                @Override
                public void onRun(int i) {
                    if (player.isOnline()) {

                    }
                }
            }, 1);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!ApplicationSQLManager.getUserNames().contains(player.getName())) {
            ApplicationPermission permission = ApplicationPermission.ATTRIBUTE_EVERYONE;
            if (player.isOp()) permission = ApplicationPermission.ATTRIBUTE_OPERATOR;
            if (player.getName().equals(RuntimeData.config.getString("ServerOwner"))) permission = ApplicationPermission.ATTRIBUTE_OWNER;
            ApplicationSQLManager.addUser(player.getName(), permission);
        }

        if (!SharingData.phones.containsKey(player.getName())) {
            SharingData.phones.put(player.getName(), new CPhone(player));
        }

        player.getInventory().setItem(0, new ItemGhastTear());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        SharingData.phones.remove(event.getPlayer().getName());
    }

    @EventHandler
    public void onTap(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInHand().getId() == SharingData.triggerItemId) {
            if (!SharingData.phones.get(player.getName()).isOpening()) {
                SharingData.phones.get(player.getName()).home();
                SharingData.phones.get(player.getName()).setOpening(true);
            }
        }
    }

}
