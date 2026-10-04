package net.superkat.tidal.compat;

import net.fabricmc.loader.api.FabricLoader;

import java.lang.reflect.Method;

/**
 * Soft, reflection-based detection of whether an Iris shader pack is currently active.
 * <p>
 * The wet overlay needs a different opacity with shaders than without: vanilla extra-softens the dithered overlay by
 * alpha-blending the surviving texels, while shaders draw them as opaque cutout. {@link net.superkat.tidal.renderer.WaveRenderer}
 * uses this to pick the matching opacity automatically (overridable via the Shader Mode config).
 * <p>
 * There is no hard dependency on Iris - every path returns {@code false} if Iris is absent or its API changes.
 */
public class IrisCompat {
    private static final boolean IRIS_PRESENT = FabricLoader.getInstance().isModLoaded("iris");

    private static Object apiInstance;
    private static Method isShaderPackInUse;
    private static boolean resolved = false;
    private static boolean resolveFailed = false;

    public static boolean isShaderPackActive() {
        if (!IRIS_PRESENT || resolveFailed) return false;
        try {
            if (!resolved) resolve();
            return (boolean) isShaderPackInUse.invoke(apiInstance);
        } catch (Throwable t) {
            resolveFailed = true; // don't keep hammering reflection if something is off
            return false;
        }
    }

    private static void resolve() throws Exception {
        Class<?> api;
        try {
            api = Class.forName("net.irisshaders.iris.api.v0.IrisApi"); // Iris 1.7+ (MC 1.20.5+)
        } catch (ClassNotFoundException e) {
            api = Class.forName("net.coderbot.iris.api.v0.IrisApi"); // legacy package
        }
        apiInstance = api.getMethod("getInstance").invoke(null);
        isShaderPackInUse = api.getMethod("isShaderPackInUse");
        resolved = true;
    }
}
