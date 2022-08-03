package furgl.hideArmor.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import furgl.hideArmor.utils.Utils;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> {
			ConfigBuilder builder = ConfigBuilder.create()
					.setParentScreen(parent)
					.setTitle(Text.translatable("config.hidearmor.name"))
					.setSavingRunnable(() -> Config.writeToFile());

			ConfigCategory category = builder.getOrCreateCategory(Text.translatable("category.hidearmor.hiddenArmor"));
			for (EquipmentSlot slot : Utils.ARMOR_SLOTS)
				category.addEntry(builder.entryBuilder()
						.startBooleanToggle(Text.translatable("option.hidearmor.hiddenArmor", slot), Config.hideYourArmor.get(slot))
						.setDefaultValue(true)
						.setSaveConsumer(value -> Config.hideYourArmor.put(slot, value))
						.build());
			for (EquipmentSlot slot : Utils.ARMOR_SLOTS)
				category.addEntry(builder.entryBuilder()
						.startBooleanToggle(Text.translatable("option.hidearmor.otherHiddenArmor", slot), Config.hideOtherPlayerArmor.get(slot))
						.setDefaultValue(true)
						.setSaveConsumer(value -> Config.hideOtherPlayerArmor.put(slot, value))
						.build());

			return builder.build();
		};
	}

}