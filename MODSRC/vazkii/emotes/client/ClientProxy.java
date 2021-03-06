package vazkii.emotes.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import vazkii.emotes.client.emote.EmoteAirGuitar;
import vazkii.emotes.client.emote.EmoteBalance;
import vazkii.emotes.client.emote.EmoteCheer;
import vazkii.emotes.client.emote.EmoteClap;
import vazkii.emotes.client.emote.EmoteExorcist;
import vazkii.emotes.client.emote.EmoteFacepalm;
import vazkii.emotes.client.emote.EmoteGangnamStyle;
import vazkii.emotes.client.emote.EmoteHeadbang;
import vazkii.emotes.client.emote.EmoteLevitate;
import vazkii.emotes.client.emote.EmoteNo;
import vazkii.emotes.client.emote.EmotePoint;
import vazkii.emotes.client.emote.EmoteRun;
import vazkii.emotes.client.emote.EmoteSalute;
import vazkii.emotes.client.emote.EmoteShaftHeadTilt;
import vazkii.emotes.client.emote.EmoteShrug;
import vazkii.emotes.client.emote.EmoteStand;
import vazkii.emotes.client.emote.EmoteThink;
import vazkii.emotes.client.emote.EmoteWave;
import vazkii.emotes.client.emote.EmoteYes;
import vazkii.emotes.client.emote.EmoteZombie;
import vazkii.emotes.client.emote.base.EmoteHandler;
import vazkii.emotes.client.emote.base.ModelAccessor;
import vazkii.emotes.common.CommonProxy;
import vazkii.emotes.common.network.PacketEmote;
import aurelienribon.tweenengine.Tween;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;

public class ClientProxy extends CommonProxy {

	public static float time = 0F;
	public static float delta = 0F;

	@Override
	public void init() {
		super.init();
		Tween.registerAccessor(ModelBiped.class, new ModelAccessor());
		FMLCommonHandler.instance().bus().register(this);
		
		initEmotes();
	}
	
	@Override
	public void handlePacket(PacketEmote message) {
		World world = Minecraft.getMinecraft().theWorld;
		EntityPlayer player = world.getPlayerEntityByName(message.playerName);
		if(player != null) {
			if(message.emoteName.equals("list"))
				player.addChatComponentMessage(EmoteHandler.buildEmoteListStr());
			else EmoteHandler.putEmote(player, message.emoteName);
		}
	}
	
	private void initEmotes() {
		EmoteHandler.emoteMap.put("airguitar", EmoteAirGuitar.class);
		EmoteHandler.emoteMap.put("balance", EmoteBalance.class);
		EmoteHandler.emoteMap.put("cheer", EmoteCheer.class);
		EmoteHandler.emoteMap.put("clap", EmoteClap.class);
		EmoteHandler.emoteMap.put("exorcist", EmoteExorcist.class);
		EmoteHandler.emoteMap.put("facepalm", EmoteFacepalm.class);
		EmoteHandler.emoteMap.put("gangnamstyle", EmoteGangnamStyle.class);
		EmoteHandler.emoteMap.put("headbang", EmoteHeadbang.class);
		EmoteHandler.emoteMap.put("levitate", EmoteLevitate.class);
		EmoteHandler.emoteMap.put("no", EmoteNo.class);
		EmoteHandler.emoteMap.put("point", EmotePoint.class);
		EmoteHandler.emoteMap.put("run", EmoteRun.class);
		EmoteHandler.emoteMap.put("salute", EmoteSalute.class);
		EmoteHandler.emoteMap.put("shaftheadtilt", EmoteShaftHeadTilt.class);
		EmoteHandler.emoteMap.put("shrug", EmoteShrug.class);
		EmoteHandler.emoteMap.put("stand", EmoteStand.class);
		EmoteHandler.emoteMap.put("think", EmoteThink.class);
		EmoteHandler.emoteMap.put("wave", EmoteWave.class);
		EmoteHandler.emoteMap.put("yes", EmoteYes.class);
		EmoteHandler.emoteMap.put("zombie", EmoteZombie.class);
	}
	
	@SubscribeEvent
	public void onRenderStart(RenderTickEvent event) {
		if(event.phase == Phase.START) {
			float ctime = (float) Math.floor(time) + event.renderTickTime;
			delta = (ctime - time) * 50;
			time = ctime;
			EmoteHandler.clearPlayerList();
		}
	}
	
	@SubscribeEvent
	public void onTick(ClientTickEvent event) {
		if(event.phase == Phase.START)
			time = (float) Math.ceil(time);
	}
}
