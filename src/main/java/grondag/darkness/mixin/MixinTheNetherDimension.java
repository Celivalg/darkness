/*******************************************************************************
 * Copyright 2019 grondag
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package grondag.darkness.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import grondag.darkness.Darkness;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.TheNetherDimension;

@Mixin(TheNetherDimension.class)
public class MixinTheNetherDimension {
    private static Vec3d darkFog = null;
    
    @Inject(method = "getFogColor", at = @At(value = "RETURN"), cancellable = true)
    private void onGetFogColor(CallbackInfoReturnable<Vec3d> ci) {
        final double factor = Darkness.darkNetherFog();
        if (factor != 1.0) {
            Vec3d result = darkFog;
            if (result == null) {
                result = ci.getReturnValue().multiply(factor);
                darkFog = result;
            }
            ci.setReturnValue(result);
        }
    }
}
